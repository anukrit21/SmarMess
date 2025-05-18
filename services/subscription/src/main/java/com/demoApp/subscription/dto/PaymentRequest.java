package com.demoApp.subscription.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private Long userId;
    private Long subscriptionId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String paymentDescription;
    private String paymentReference;
    private String paymentStatus;
    private String paymentTransactionId;
    private String paymentError;
    private String paymentErrorMessage;
    private String paymentErrorCode;
    private String paymentErrorType;
    private String paymentErrorCategory;
    private String paymentErrorSubCategory;
    private String paymentErrorReason;
    private String paymentErrorAction;
    private String paymentErrorSource;
    private String paymentErrorSourceType;
    private String paymentErrorSourceId;
    private String paymentErrorSourceName;
    private String paymentErrorSourceDescription;
    private String paymentErrorSourceCategory;
    private String paymentErrorSourceSubCategory;
    private String paymentErrorSourceReason;
    private String paymentErrorSourceAction;
    
    // Explicit getter methods
    public Long getUserId() {
        return userId;
    }
    
    public Long getSubscriptionId() {
        return subscriptionId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public String getPaymentDescription() {
        return paymentDescription;
    }
    
    public String getPaymentReference() {
        return paymentReference;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }
    
    public String getPaymentError() {
        return paymentError;
    }
    
    public String getPaymentErrorMessage() {
        return paymentErrorMessage;
    }
    
    public String getPaymentErrorCode() {
        return paymentErrorCode;
    }
    
    public String getPaymentErrorType() {
        return paymentErrorType;
    }
    
    public String getPaymentErrorCategory() {
        return paymentErrorCategory;
    }
    
    public String getPaymentErrorSubCategory() {
        return paymentErrorSubCategory;
    }
    
    public String getPaymentErrorReason() {
        return paymentErrorReason;
    }
    
    public String getPaymentErrorAction() {
        return paymentErrorAction;
    }
    
    public String getPaymentErrorSource() {
        return paymentErrorSource;
    }
    
    public String getPaymentErrorSourceType() {
        return paymentErrorSourceType;
    }
    
    public String getPaymentErrorSourceId() {
        return paymentErrorSourceId;
    }
    
    public String getPaymentErrorSourceName() {
        return paymentErrorSourceName;
    }
    
    public String getPaymentErrorSourceDescription() {
        return paymentErrorSourceDescription;
    }
    
    public String getPaymentErrorSourceCategory() {
        return paymentErrorSourceCategory;
    }
    
    public String getPaymentErrorSourceSubCategory() {
        return paymentErrorSourceSubCategory;
    }
    
    public String getPaymentErrorSourceReason() {
        return paymentErrorSourceReason;
    }
    
    public String getPaymentErrorSourceAction() {
        return paymentErrorSourceAction;
    }
    
    // Explicit setter methods
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }
    
    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }
    
    public void setPaymentError(String paymentError) {
        this.paymentError = paymentError;
    }
    
    public void setPaymentErrorMessage(String paymentErrorMessage) {
        this.paymentErrorMessage = paymentErrorMessage;
    }
    
    public void setPaymentErrorCode(String paymentErrorCode) {
        this.paymentErrorCode = paymentErrorCode;
    }
    
    public void setPaymentErrorType(String paymentErrorType) {
        this.paymentErrorType = paymentErrorType;
    }
    
    public void setPaymentErrorCategory(String paymentErrorCategory) {
        this.paymentErrorCategory = paymentErrorCategory;
    }
    
    public void setPaymentErrorSubCategory(String paymentErrorSubCategory) {
        this.paymentErrorSubCategory = paymentErrorSubCategory;
    }
    
    public void setPaymentErrorReason(String paymentErrorReason) {
        this.paymentErrorReason = paymentErrorReason;
    }
    
    public void setPaymentErrorAction(String paymentErrorAction) {
        this.paymentErrorAction = paymentErrorAction;
    }
    
    public void setPaymentErrorSource(String paymentErrorSource) {
        this.paymentErrorSource = paymentErrorSource;
    }
    
    public void setPaymentErrorSourceType(String paymentErrorSourceType) {
        this.paymentErrorSourceType = paymentErrorSourceType;
    }
    
    public void setPaymentErrorSourceId(String paymentErrorSourceId) {
        this.paymentErrorSourceId = paymentErrorSourceId;
    }
    
    public void setPaymentErrorSourceName(String paymentErrorSourceName) {
        this.paymentErrorSourceName = paymentErrorSourceName;
    }
    
    public void setPaymentErrorSourceDescription(String paymentErrorSourceDescription) {
        this.paymentErrorSourceDescription = paymentErrorSourceDescription;
    }
    
    public void setPaymentErrorSourceCategory(String paymentErrorSourceCategory) {
        this.paymentErrorSourceCategory = paymentErrorSourceCategory;
    }
    
    public void setPaymentErrorSourceSubCategory(String paymentErrorSourceSubCategory) {
        this.paymentErrorSourceSubCategory = paymentErrorSourceSubCategory;
    }
    
    public void setPaymentErrorSourceReason(String paymentErrorSourceReason) {
        this.paymentErrorSourceReason = paymentErrorSourceReason;
    }
    
    public void setPaymentErrorSourceAction(String paymentErrorSourceAction) {
        this.paymentErrorSourceAction = paymentErrorSourceAction;
    }
}
