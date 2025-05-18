# Service API Documentation

This document provides a summary of all REST API endpoints (functions) for each service in the application. Each section lists the endpoints, HTTP methods, and a short description for each function, grouped by service.

---

## Payment Service

**Base URL:** `/api/v1/payments`, `/api/v1/payment-methods`, `/api/v1/payments/admin`, `/api/v1/payments/webhook`, `/checkout`

### Endpoints:
- **POST** `/api/v1/payments` — Process a payment
- **GET** `/api/v1/payments/{paymentId}` — Get payment details
- **POST** `/api/v1/payments/{paymentId}/refund` — Process a refund
- **GET** `/api/v1/payments/analytics` — Get payment analytics
- **POST** `/api/v1/payment-methods` — Add a new payment method
- **PUT** `/api/v1/payment-methods/{id}/default` — Set default payment method
- **DELETE** `/api/v1/payment-methods/{id}` — Delete payment method
- **POST** `/api/v1/payments/webhook/stripe` — Handle Stripe webhook events
- **GET** `/checkout/{orderId}` — Render the checkout page
- **GET** `/checkout/success` — Payment success page
- **GET** `/checkout/cancel` — Payment cancel page
- **GET** `/api/v1/payments/admin` — Get all payments (admin)
- **POST** `/api/v1/payments/admin/refund/{paymentId}` — Force refund (admin)
- **GET** `/api/v1/payments/admin/stats` — Get payment statistics (admin)

---

## Mess Service

**Base URLs:** `/api/v1/mess`, `/api/v1/orders`, `/api/v1/deliveries`, `/api/v1/delivery-persons`, `/api/v1/subscriptions`, `/api/v1/user-subscriptions`, `/api/recommendations`, `/api/enhanced-recommendations`

### Endpoints:
- **POST** `/api/v1/mess` — Create mess
- **PUT** `/api/v1/mess/{messId}` — Update mess
- **GET** `/api/v1/mess` — Get all messes
- **GET** `/api/v1/mess/{messId}/menu` — Get mess menu
- **GET** `/api/v1/mess/{messId}/analytics` — Get mess analytics
- **GET** `/api/v1/orders` — Get all orders
- **GET** `/api/v1/orders/{id}` — Get order by ID
- **GET** `/api/v1/orders/mess/{messId}` — Get orders by mess
- **GET** `/api/v1/orders/user/{userId}` — Get orders by user
- **PUT** `/api/v1/orders/{id}/status` — Update order status
- **PUT** `/api/v1/orders/{id}/cancel` — Cancel order
- **GET** `/api/v1/deliveries` — Get all deliveries
- **GET** `/api/v1/deliveries/paged` — Get deliveries with pagination
- **PUT** `/api/v1/deliveries/{id}/resolve` — Resolve delivery issue
- **PUT** `/api/v1/deliveries/{id}/cancel` — Cancel delivery
- **GET** `/api/recommendations/messes` — Get mess recommendations
- **GET** `/api/recommendations/test` — Get test recommendations
- **GET** `/api/enhanced-recommendations/messes` — Get enhanced mess recommendations
- **GET** `/api/enhanced-recommendations/test` — Get test enhanced recommendations

---

## Menu Module Service

**Base URLs:** `/api/v1/menu-categories`, `/api/v1/menu-items`, `/api/v1/menus`

### Endpoints:
- **GET** `/api/v1/menu-categories` — Get all menu categories
- **GET** `/api/v1/menu-categories/{id}` — Get menu category by ID
- **POST** `/api/v1/menu-categories` — Create menu category
- **PUT** `/api/v1/menu-categories/{id}` — Update menu category
- **DELETE** `/api/v1/menu-categories/{id}` — Delete menu category
- **PATCH** `/api/v1/menu-categories/{id}/toggle-active` — Toggle category active status
- **GET** `/api/v1/menu-items` — Get all menu items
- **GET** `/api/v1/menu-items/{id}` — Get menu item by ID
- **POST** `/api/v1/menu-items` — Create menu item
- **PUT** `/api/v1/menu-items/{id}` — Update menu item
- **PATCH** `/api/v1/menu-items/{id}/toggle-availability` — Toggle menu item availability
- **GET** `/api/v1/menus` — Get all menus
- **GET** `/api/v1/menus/{id}` — Get menu by ID
- **POST** `/api/v1/menus` — Create menu
- **PUT** `/api/v1/menus/{id}` — Update menu
- **DELETE** `/api/v1/menus/{id}` — Delete menu
- **PATCH** `/api/v1/menus/{id}/toggle-active` — Toggle menu active status

---

## Order Service

**Base URL:** `/api/orders`

### Endpoints:
- **POST** `/api/orders` — Create order
- **GET** `/api/orders/{orderId}` — Get order by ID
- **PUT** `/api/orders/{orderId}/status` — Update order status
- **PUT** `/api/orders/{orderId}/payment` — Update payment status
- **PUT** `/api/orders/{orderId}/delivery` — Assign delivery person
- **GET** `/api/orders/mess/{messId}` — Get mess orders
- **GET** `/api/orders/user/{userId}` — Get user orders
- **GET** `/api/orders/analytics` — Get order analytics

---

## User Service

**Base URLs:** `/api/v1/users`, `/api/v1/auth`, `/actuator`

### Endpoints:
- **POST** `/api/v1/users/register` — Register user
- **GET** `/api/v1/users/{id}` — Get user by ID
- **PUT** `/api/v1/users/{id}` — Update user
- **PUT** `/api/v1/users/{id}/categories` — Update user categories
- **PUT** `/api/v1/users/{id}/preferences` — Update user preferences
- **POST** `/api/v1/users/{userId}/favorites` — Add favorite
- **DELETE** `/api/v1/users/{userId}/favorites` — Remove favorite
- **POST** `/api/v1/users/{userId}/ratings` — Add rating
- **PUT** `/api/v1/users/{userId}/ratings` — Update rating
- **PUT** `/api/v1/users/{id}/profile-image` — Update profile image
- **GET** `/api/v1/users/category/{category}` — Get users by category
- **GET** `/api/v1/users/preferred-category/{category}` — Get users by preferred category
- **GET** `/actuator/health` — Health check
- **GET** `/actuator/health/details` — Detailed health check
- **GET** `/actuator/info` — Application info

---

## Authentication Service

**Base URLs:** `/api/auth`, `/actuator`

### Endpoints:
- **POST** `/api/auth/register` — Register user
- **POST** `/api/auth/login` — User login
- **POST** `/api/auth/logout` — Logout
- **POST** `/api/auth/refresh` — Refresh token
- **POST** `/api/auth/validate` — Validate token
- **POST** `/api/auth/forgot-password` — Request password reset
- **POST** `/api/auth/reset-password` — Reset password
- **POST** `/api/auth/change-password` — Change password
- **POST** `/api/auth/roles/{userId}/add` — Add role to user
- **POST** `/api/auth/roles/{userId}/remove` — Remove role from user
- **GET** `/api/auth/roles/{userId}` — Get user roles
- **POST** `/api/auth/mfa/setup` — Setup MFA
- **POST** `/api/auth/mfa/verify` — Verify MFA
- **POST** `/api/auth/mfa/disable` — Disable MFA
- **POST** `/api/auth/oauth/login` — OAuth login
- **POST** `/api/auth/account/unlock` — Unlock account
- **GET** `/api/auth/account/lock-status` — Get account lock status
- **GET** `/actuator/health` — Health check
- **GET** `/actuator/health/details` — Detailed health check
- **GET** `/actuator/info` — Application info

---

*For more details on request/response bodies, refer to the respective service source code or Swagger/OpenAPI documentation if available.*
