Finance Dashboard Backend

Register User
Endpoint: POST /api/auth/register
Description: Creates a new user with default role VIEWER
🔹 Login User
Endpoint: POST /api/auth/login
Description: Authenticates user and returns JWT token
🔹 Authentication Flow
After login, a JWT token is generated
Token must be included in all protected requests:
Authorization: Bearer <your_token>
✅ Assumptions Made
All new users are assigned the VIEWER role by default
Only ADMIN users can modify roles (planned feature)
System uses stateless authentication (JWT)
Single backend service (no microservices for simplicity)
Passwords are securely stored using BCrypt hashing
⚖️ Trade-offs Considered
JWT vs Session-Based Auth
→ Chose JWT for scalability and stateless design
Default Role Restriction
→ Prevented users from assigning roles during registration to avoid security risks
Static Secret Key
→ Used hardcoded key for simplicity (can be moved to environment variables in production)
No Refresh Tokens
→ Skipped for initial implementation to keep system simple