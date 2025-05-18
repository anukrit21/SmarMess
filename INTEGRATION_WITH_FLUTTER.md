# Integrating Flutter Frontend with Spring Boot Backend

This guide explains how your frontend team can connect a Flutter app to the backend services provided by your Spring Boot microservices project.

---

## 1. Backend Overview
- The backend exposes RESTful APIs (JSON) via HTTP.
- Each microservice (order-service, mess-service, authentication, etc.) runs on a specific port (e.g., 8771, 8769).
- Example base URLs:
  - `http://localhost:8771/api/orders`
  - `http://localhost:8769/api/mess`

---

## 2. Required Flutter Packages
Add to your `pubspec.yaml`:
```yaml
http: ^1.2.0
# or for advanced usage:
dio: ^5.0.0
```

---

## 3. Making HTTP Requests
Example using the `http` package:
```dart
import 'package:http/http.dart' as http;
import 'dart:convert';

final response = await http.get(Uri.parse('http://localhost:8771/api/orders'));
if (response.statusCode == 200) {
  final data = jsonDecode(response.body);
  // Use the data
}
```

---

## 4. Authentication (JWT)
- After login (POST to `/api/auth/login`), the backend returns a JWT.
- Store the token (e.g., with `shared_preferences`).
- Include it in the `Authorization` header for protected endpoints:
```dart
final response = await http.get(
  Uri.parse('http://localhost:8771/api/orders'),
  headers: {
    'Authorization': 'Bearer <your_jwt_token>',
    'Content-Type': 'application/json',
  },
);
```

---

## 5. API Endpoints
- Each service exposes its own REST endpoints.
- Common endpoints:
  - `/api/auth/login` – Authenticate and get JWT
  - `/api/orders` – Order management
  - `/api/mess` – Mess management
  - `/api/users` – User management
- Ask the backend team for a full endpoint list or API docs if needed.

---

## 6. CORS (Cross-Origin Resource Sharing)
- The backend should have CORS enabled for frontend access.
- If you see CORS errors, ask the backend team to allow your frontend's origin.

---

## 7. Request/Response Format
- All requests and responses use JSON.
- Set headers:
```dart
headers: {'Content-Type': 'application/json'}
```
- Example POST:
```dart
final response = await http.post(
  Uri.parse('http://localhost:8771/api/orders'),
  headers: {'Content-Type': 'application/json'},
  body: jsonEncode({'userId': 1, 'messId': 2, ...}),
);
```

---

## 8. Environment Configuration
- For local testing, use `localhost` or your machine’s IP.
- For production, update the base URLs to match the deployment server.

---

## 9. Error Handling
- The backend returns standard HTTP status codes (200, 201, 400, 401, 404, 500, etc.).
- Handle errors in Flutter using `response.statusCode` and display appropriate messages.

---

## 10. Testing
- Test backend endpoints using Postman or cURL before integrating with Flutter.
- Example:
```
curl -X GET http://localhost:8771/api/orders
```

---

## 11. Sample Login Flow
```dart
final loginResponse = await http.post(
  Uri.parse('http://localhost:8771/api/auth/login'),
  headers: {'Content-Type': 'application/json'},
  body: jsonEncode({'username': 'user', 'password': 'pass'}),
);
if (loginResponse.statusCode == 200) {
  final token = jsonDecode(loginResponse.body)['token'];
  // Store token and use for subsequent requests
}
```

---

## 12. Contact Backend Team
- For endpoint details, request/response examples, or troubleshooting, contact the backend team.

---

**Your backend is fully compatible with Flutter. Follow these steps to integrate and test your frontend with the backend APIs.**
