# Microservice Port Assignments

This document lists the HTTP port numbers configured for each microservice in your application. If duplicate ports are found, suggested new port numbers are provided for uniqueness.

| Service Name         | Port |
|----------------------|------|
| payment-service      | 8783 |
| mess-service         | 8769 |
| menu-module          | 8770 |
| order-service        | 8771 |
| user-service         | 8772 |
| authentication       | 8773 |
| admin-service        | 8774 |
| owner-service        | 8775 |
| delivery-service     | 8768 |
| otp-service          | 8776 |
| campus-module        | 8777 |
| subscription-service | 8778 |
| config-server        | 8762 |
| eureka-server        | 8761 |
| common-kafka         | (n/a) |



**How to update:**
Add the following to the top of each `application.yml` (if not present):
```yaml
server:
  port: <unique_port>
```

