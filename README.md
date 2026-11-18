
# Todac - Spring Boot API Server

감정 일기장 **Todac**의 백엔드(Spring Boot) 서버 레포지토리입니다.  
사용자가 작성한 일기를 기반으로 **AI가 코멘트(위로/격려 등)** 를 생성하고, **자동 태그**를 달아 검색을 돕고, **월별 감정 통계(긍정/부정 비율)** 를 제공하는 것을 목표로 합니다.

- Developer: **@YimTaeKeun**

---

## ✨ 핵심 기능 (Goals)

- **일기 작성/조회/수정/삭제**
- **AI 코멘트 자동 생성**
  - 예: 힘든 일을 적으면 위로의 말을 코멘트로 제공
- **AI 태그 자동 생성**
  - 검색/필터링을 쉽게 하기 위한 태그(예: `#스트레스`, `#성취`, `#가족`)
- **월별 감정 통계**
  - 한 달 동안 긍정/부정 일기 개수 및 비율 요약

> 프론트엔드(별도 레포/프로젝트):
> - Web: React.js
> - App: Flutter

---

## 🧱 Tech Stack

- **Java / Spring Boot**
- Spring Web / Validation
- Spring Data JPA (DB 연동)
- (선택) Spring Security (인증/인가가 필요한 경우)
- (선택) Swagger/OpenAPI (API 문서화)
- (선택) Docker / Docker Compose (배포/로컬 DB)

> AI 코멘트/태그 생성은 외부 AI Provider(OpenAI 등) 또는 자체 모델을 **플러그인 형태로 연동**하는 구성을 지향합니다.

---

## 📌 Architecture (High Level)

- Client(React / Flutter)
  → **Todac API Server**
  → DB (Diary, Tag, User, AIComment 등)
  → AI Provider (Comment/Tag Generation)

---

## ✅ Requirements

- JDK 17+ (권장)
- Gradle (Wrapper 사용 권장)
- DB (예: MySQL/PostgreSQL/H2 중 택1)

---

## ⚙️ Configuration

`application.yml` 또는 `.env`(도커/런타임 방식에 따라)로 환경변수를 관리합니다.

예시(가이드용):

```bash
# Server
SERVER_PORT=8080

# Database
DB_URL=jdbc:mysql://localhost:3306/todac
DB_USERNAME=root
DB_PASSWORD=your_password

# AI Provider (예: OpenAI 등)
AI_PROVIDER=openai
AI_API_KEY=your_ai_api_key
AI_MODEL=your_model_name
````

> 위 키 이름은 예시이며, 프로젝트 설정에 맞게 조정하시면 됩니다.

---

## 🚀 Run (Local)

### 1) Clone

```bash
git clone https://github.com/YimTaeKeun/todac.git
cd todac
```

### 2) Build

```bash
./gradlew clean build
```

### 3) Start

```bash
./gradlew bootRun
```

서버 기본 주소:

* `http://localhost:8080`

---

## 🧪 API (Example / Draft)

> 아래는 “예시”이며, 실제 구현에 따라 변경될 수 있습니다.

### 일기 작성

`POST /diary`

Request

```json
{
  "content": "오늘은 너무 지쳤다. 일이 계속 꼬였다.",
  "writtenAt": "2025-12-17"
}
```

Response (예시)

```json
{
  "diaryId": 1,
  "content": "오늘은 너무 지쳤다. 일이 계속 꼬였다.",
  "aiComment": "많이 힘드셨을 것 같아요. 오늘 하루만큼은 스스로를 탓하지 않으셔도 괜찮습니다.",
  "tags": ["스트레스", "피로", "업무"],
  "sentiment": "NEGATIVE",
  "createdAt": "2025-12-17T14:00:00"
}
```

### 태그 검색

`GET /api/v1/diaries?tag=스트레스&from=2025-12-01&to=2025-12-31`

### 월별 통계

`GET /api/v1/stats/monthly?month=2025-12`

Response (예시)

```json
{
  "month": "2025-12",
  "positiveCount": 8,
  "negativeCount": 12,
  "total": 20
}
```

---

## 🗂️ Project Structure (Example)

```text
src/main/java
 ├─ domain
 │   ├─ diary
 │   ├─ tag
 │   └─ stats
 ├─ api
 │   ├─ controller
 │   ├─ request
 │   └─ response
 ├─ ai
 │   ├─ client
 │   ├─ prompt
 │   └─ service
 └─ global
     ├─ config
     ├─ exception
     └─ utils
```

---

## 🔒 Security (Optional)

서비스 확장 시 아래를 고려할 수 있습니다.

* JWT 기반 인증
* OAuth 소셜 로그인
* 사용자별 데이터 접근 제어(본인 일기만 조회/수정)

---

## 🧭 Roadmap

* [ ] Diary CRUD + 기본 검색
* [ ] AI Comment 생성 연동
* [ ] AI Tag 생성 연동
* [ ] 월별 통계 API
* [ ] API 문서화(Swagger/OpenAPI)
* [ ] 배포 파이프라인(Docker/CI)

---


## 📄 License

추후 명시 예정 (TBD)

---

### Contact

* GitHub: **@YimTaeKeun**
