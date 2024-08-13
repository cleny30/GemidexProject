# Gemidex Project

## Overview

The Gemidex project consists of two main components:

1. **Gemidex (Android Studio Project)**: An Android application designed to scan Pokémon and other objects, providing detailed information about them. If the user is logged in, the app also stores the scanned data for future reference.
2. **GemidexAPI (ASP.NET RESTful API)**: A backend API that supports the Gemidex application by providing data and managing user accounts.

## Directory Structure

/Gemidex
├── [Android Studio Project Files]
├── app/
│ ├── com.example.gemidex
│ ├── Constant
│ ├── Model
│ └── Service
├── gradle/
├── build.gradle
└── settings.gradle

/GemidexAPI
├── [ASP.NET API Project Files]
├── Controllers/
│ ├── AccountController.cs
│ └── GemidexController.cs
├── Core
│ └── DbHelperExtentions.cs
├── Models/
│ ├── Entity
│ └── Dto
├── DataAccess
│ └── GemidexContext.cs
├── appsettings.json
└── GemidexAPI.csproj

## Installation

### Set Up

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/cleny30/Gemidex.git
    ```

2. **Open the Project**:
    - **Android app**:
        - Launch Android Studio.
        - Select **Open an Existing Project** from the welcome screen.
        - Navigate to the `Gemidex` folder and select it.
    - **API**:
        - Launch Visual Studio.
        - Select **Open an Existing Project** from the welcome screen.
        - Navigate to the `GemidexAPI` folder and select it.

### Gemidex (Android Studio Project)

#### Prerequisites

- **Android Studio**: [Download and Install](https://developer.android.com/studio)
- **Java Development Kit (JDK)**: Version 8 or higher
- **Android SDK**: Ensure that your SDK is up-to-date

### Configuration

- **API Base URL**: 
    - Navigate to `app/src/main/java/Constant/AppConstants.java`.
    - Update the `BASE_URL` with your computer's IP address (e.g., `http://192.168.x.x:5000/` for local development).

### Usage

- **Scanning Pokémon and Objects**: Use the app's scanning functionality to detect Pokémon and other objects. The app will display detailed information about the scanned items.
- **User Authentication**: Log in to the app to store the scanned data. This data can be accessed later and helps in maintaining a personalized experience.

## GemidexAPI (ASP.NET RESTful API)

### Prerequisites

- **.NET SDK**: Version 5.0 or higher. [Download and Install](https://dotnet.microsoft.com/download)
- **Database**: SQL Server or other compatible databases.

### Getting Started

- Inside `Program.cs`, update `builder.WithOrigins("http://192.168.x.x")` with your IP address to allow connections from your real phone.

### Configuration

- **Database Connection**:
    - Open `appsettings.json` in the `GemidexAPI` folder.
    - Update the connection string with your database details:

    ```json
    "ConnectionStrings": {
      "DefaultConnection": "Server=localhost;Database=GemidexDb;User Id=yourusername;Password=yourpassword;"
    }
    ```

### API Endpoints

- **Base URL**: `http://192.168.x.x:5068/`

    - **GET /api/Account/User**: Retrieve information about a user by email.
    - **POST /api/Account/Register**: Register a new account using Google account.

## Contact

For any questions or support, please contact Cleny Nguyen at chihau30@gmail.com.

- **GitHub**: [https://github.com/cleny30](https://github.com/cleny30)
- **Email**: [chihau30@gmail.com](mailto:chihau30@gmail.com)
