# Backend to Flutter Integration: Step-by-Step Guide

This guide provides clear, actionable steps for connecting your Spring Boot backend services to your Flutter frontend. It covers backend requirements, what code is needed, and how the Flutter app should interact with the backend.

---

## 1. Backend Requirements
- **Expose RESTful APIs**: All backend services should have REST endpoints (e.g., `/api/v1/...`).
- **Enable CORS**: Cross-Origin Resource Sharing must be enabled on every backend service to allow requests from the Flutter frontend.
- **No Flutter-specific code is needed in the backend.** Only CORS and accessible APIs are required.

---

## 2. Backend Code: CORS Configuration Example
For each service, your `SecurityConfig.java` should include:
```java
@Bean
public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
    org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
    configuration.addAllowedOriginPattern("*"); // Allow all origins (for dev)
    configuration.addAllowedMethod("*");        // Allow all HTTP methods
    configuration.addAllowedHeader("*");        // Allow all headers
    configuration.setAllowCredentials(true);     // Allow credentials (cookies, auth headers)
    org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```
And reference it in your security filter chain:
```java
.cors(cors -> cors.configurationSource(corsConfigurationSource()))
```

---

## 3. How the Flutter Frontend Connects
1. **Set the backend base URL** in your Flutter app (e.g., `http://localhost:8080` or your deployed backend URL).
2. **Use the `http` or `dio` Dart package** to make API calls.
3. **Example Dart code:**
```dart
import 'package:http/http.dart' as http;

final response = await http.get(
  Uri.parse('http://<backend-ip>:<port>/api/v1/your-endpoint'),
  headers: {
    'Authorization': 'Bearer <your-jwt-token>', // For secured endpoints
    'Content-Type': 'application/json',
  },
);
```
4. **Handle responses and errors** in your Flutter app as per usual REST API practices.

---

## 4. Step-by-Step Checklist
1. **Start your backend services.**
2. **Confirm CORS is enabled** (see code above; already done for all your services).
3. **In your Flutter app:**
    - Set the backend base URL.
    - Use Dart's `http`/`dio` to call backend APIs.
    - Add JWT or other auth headers if needed.
4. **Test API calls** from Flutter (or Postman) to ensure connectivity.

---

## 5. No Extra Backend Code Needed
- No additional code or dependencies are required in the backend for Flutter integration.
- As long as CORS is enabled and APIs are accessible, the frontend can connect.

---

## 6. Troubleshooting
- **CORS errors:** Double-check the CORS config in each service.
- **Network errors:** Ensure backend and frontend can reach each other (correct IP, port, firewall, etc.).

---

If you add new backend services in the future, repeat the CORS configuration step above.
