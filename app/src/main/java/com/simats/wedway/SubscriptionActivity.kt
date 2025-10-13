package com.simats.wedway

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.*
import com.simats.wedway.R

class SubscriptionActivity : AppCompatActivity(), PurchasesUpdatedListener {

    private lateinit var btnSubscribe: Button
    private lateinit var btnSkipForNow: Button
    private lateinit var billingClient: BillingClient
    private var productDetails: ProductDetails? = null

    companion object {
        private const val TAG = "SubscriptionActivity"
        private const val SUBSCRIPTION_ID = "univault_premium_subscription"
        private const val TEST_SUBSCRIPTION_ID = "android.test.purchased"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        btnSubscribe = findViewById(R.id.btnSubscribe)
        btnSkipForNow = findViewById(R.id.btnSkipForNow)

        setupClickListeners()
        setupBillingClient()
    }

    private fun setupClickListeners() {
        btnSkipForNow.setOnClickListener {
            Toast.makeText(this, "Skipped subscription", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, UserLoginActivity::class.java))
            finish()
        }

        btnSubscribe.setOnClickListener {
            launchSubscriptionFlow()
        }
    }

    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(this)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryProductDetails()
                } else {
                    Log.e(TAG, "Billing setup failed: ${billingResult.debugMessage}")
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.w(TAG, "Billing service disconnected")
            }
        })
    }

    private fun queryProductDetails() {
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(SUBSCRIPTION_ID)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        )

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (productDetailsList.isNotEmpty()) {
                    productDetails = productDetailsList[0]
                    Log.d(TAG, "ProductDetails fetched successfully")
                } else {
                    Log.w(TAG, "No products found")
                }
            } else {
                Log.e(TAG, "Error fetching product details: ${billingResult.debugMessage}")
            }
        }
    }

    private fun launchSubscriptionFlow() {
        if (productDetails == null) {
            Toast.makeText(this, "Subscription not available", Toast.LENGTH_SHORT).show()
            return
        }

        val offerToken = productDetails?.subscriptionOfferDetails?.firstOrNull()?.offerToken

        if (offerToken == null) {
            Toast.makeText(this, "No subscription offer available", Toast.LENGTH_SHORT).show()
            return
        }

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails!!)
                        .setOfferToken(offerToken)
                        .build()
                )
            )
            .build()

        billingClient.launchBillingFlow(this, billingFlowParams)
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> purchases?.forEach { handlePurchase(it) }
            BillingClient.BillingResponseCode.USER_CANCELED ->
                Toast.makeText(this, "Purchase canceled", Toast.LENGTH_SHORT).show()
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED ->
                Toast.makeText(this, "Already subscribed", Toast.LENGTH_SHORT).show()
            else ->
                Toast.makeText(this, "Purchase failed: ${billingResult.debugMessage}", Toast.LENGTH_LONG).show()
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
            val acknowledgeParams = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
            billingClient.acknowledgePurchase(acknowledgeParams) { result ->
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    Toast.makeText(this, "Subscription successful!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::billingClient.isInitialized) billingClient.endConnection()
    }
}
