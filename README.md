# 🛒 SpringBoot Ecommerce API

A full-featured **RESTful Ecommerce Backend** built with Spring Boot, featuring JWT authentication, Google OAuth2, product management, cart, orders, and more — fully containerized with Docker.

---

## 🚀 Tech Stack

| Technology | Purpose |
|---|---|
| Java + Spring Boot | Backend Framework |
| Spring Security + JWT | Authentication & Authorization |
| Google OAuth2 | Social Login |
| PostgreSQL | Relational Database |
| Spring Data JPA + Hibernate | ORM & Database Layer |
| Docker + Docker Compose | Containerization |
| Swagger UI (OpenAPI 3.1) | API Documentation |

---

## ✨ Features

- ✅ User Registration & Login with JWT (Access + Refresh Token)
- ✅ Google OAuth2 Social Login
- ✅ Role-based Access Control (ADMIN / USER)
- ✅ Cookie-based JWT with Refresh Token support
- ✅ Category Management (CRUD)
- ✅ Product Management (CRUD + Image Upload + Keyword Search)
- ✅ Cart Management (Add, Update Quantity, Remove)
- ✅ Address Management (CRUD)
- ✅ Order Placement with Payment Method
- ✅ Full Swagger UI Documentation

---

## 🐳 Run With Docker (Recommended)

> No need to install Java, Maven, or PostgreSQL locally. Just Docker!

### Prerequisites
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running

### Step 1: Clone the repository
```bash
git clone https://github.com/ChirayuP19/E-commerce-Monolithic
cd sb-ecom
```

### Step 2: Create `.env` file
Copy the example file and fill in your real values:
```bash
cp .env.example .env
```
Then open `.env` and replace with your actual Google credentials:
```env
GOOGLE_CLIENT_ID=your_actual_google_client_id
GOOGLE_CLIENT_SECRET=your_actual_google_client_secret
```
> ⚠️ Never commit `.env` to GitHub — it contains your secrets

### Step 3: Start everything
```bash
docker compose up
```

That's it! All 3 services start automatically:

| Service | URL |
|---|---|
| 🌐 Swagger UI (API Docs) | http://localhost:8080/swagger-ui.html |
| 🐘 pgAdmin (DB Manager) | http://localhost:5051 |
| 🗄️ PostgreSQL | localhost:5434 |

### Stop everything
```bash
docker compose down
```

---

## 📦 Docker Hub

The app image is publicly available on Docker Hub:

```bash
docker pull chirayupatel19/sb-ecom:latest
```

---

## 📚 API Endpoints

### 🔐 Auth Controller
| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/v1/auth/signup` | Register new user | Public |
| POST | `/api/v1/auth/signin` | Login with JWT | Public |
| POST | `/api/v1/auth/signout` | Logout & clear cookies | Authenticated |
| POST | `/api/v1/auth/refresh` | Refresh access token | Authenticated |
| GET | `/api/v1/auth/username` | Get current username | Authenticated |
| GET | `/api/v1/auth/userDetails` | Get current user details | Authenticated |
| GET | `/api/v1/auth/oauth2/success` | Google OAuth2 callback | Public |

---

### 📂 Category Controller
| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/v1/public/categories` | Get all categories | Public |
| POST | `/api/v1/public/categories` | Create category | Admin |
| PUT | `/api/v1/public/categories/{id}` | Update category | Admin |
| DELETE | `/api/v1/public/categories/{id}` | Delete category | Admin |

---

### 📦 Product Controller
| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/v1/public/products` | Get all products | Public |
| GET | `/api/v1/public/products/keyword/{keyword}` | Search by keyword | Public |
| GET | `/api/v1/public/categories/{categoryId}/products` | Get products by category | Public |
| POST | `/api/v1/admin/categories/{categoryId}/product` | Create product | Admin |
| PUT | `/api/v1/admin/products/{productId}` | Update product | Admin |
| PATCH | `/api/v1/admin/products/{productId}` | Partial update | Admin |
| DELETE | `/api/v1/admin/products/{productId}` | Delete product | Admin |
| PUT | `/api/v1/products/{productId}/image` | Upload product image | Admin |

---

### 🛒 Cart Controller
| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/v1/carts` | Get all carts | Admin |
| GET | `/api/v1/carts/user/cart` | Get current user cart | Authenticated |
| POST | `/api/v1/carts/products/{productId}/quantity/{quantity}` | Add to cart | Authenticated |
| PUT | `/api/v1/cart/product/{productId}/quantity/{operation}` | Update quantity | Authenticated |
| DELETE | `/api/v1/carts/{cartId}/product/{productId}` | Remove from cart | Authenticated |

---

### 📍 Address Controller
| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/v1/addresses` | Get all addresses | Admin |
| GET | `/api/v1/addresses/{addressId}` | Get address by ID | Authenticated |
| GET | `/api/v1/users/addresses` | Get current user addresses | Authenticated |
| POST | `/api/v1/addresses` | Create address | Authenticated |
| PUT | `/api/v1/addresses/{addressId}` | Update address | Authenticated |
| DELETE | `/api/v1/addresses/{addressId}` | Delete address | Authenticated |

---

### 📋 Order Controller
| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/v1/order/users/payments/{paymentMethod}` | Place order | Authenticated |

---

## 🔑 Authentication Flow

```
1. POST /api/v1/auth/signup       → Register user
2. POST /api/v1/auth/signin       → Get JWT token (stored in cookie)
3. Use JWT cookie in requests     → Access protected endpoints
4. POST /api/v1/auth/refresh      → Get new Jwt token when JWT expired no need to SignIn again until refresh Token valid.
5. POST /api/v1/auth/signout      → Clear JWT cookies
```

---

## 🗂️ Project Structure

```
src/
├── controllers/        # REST API Controllers
├── services/           # Business Logic
├── repositories/       # Database Layer (JPA)
├── models/             # Entity Classes
├── dto/                # Data Transfer Objects
├── security/           # JWT + OAuth2 Config
└── exceptions/         # Global Exception Handling
```

---

## 👤 Author

**Chirayu Patel**
- GitHub: [@chirayupatel19](https://github.com/ChirayuP19)
- Docker Hub: [chirayupatel19/sb-ecom](https://hub.docker.com/r/chirayupatel19/sb-ecom)

---

## 📄 License

This project is licensed under the **Apache 2.0 License**.
