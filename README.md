# HNDIT Academic Toolkit (Android - Java & XML)

An offline-first, native Android application specifically tailored for **HNDIT (Higher National Diploma in Information Technology)** students at **ATI Galle (SLIATE)**. Built using Java and XML, this lightweight application operates completely offline without requiring any backend or Firebase service.

---

## 🌟 Key Features & Modules

1. **Semester GPA Calculator (`GpaActivity`)**
   - Accurately calculates semester GPA.
   - Supports elective/optional subjects: selecting **"N/A"** excludes the subject from GPA calculations.

2. **Target CGPA Predictor (`TargetGpaActivity`)**
   - Estimates required future GPAs to achieve a desired target CGPA.
   - Uses the default total program credit load of **56 credits** (with manual adjustment options).

3. **Attendance Eligibility Checker (`AttendanceActivity`)**
   - Tracks lecture attendance based on **Lecture Days** (rather than hours).
   - Evaluates student eligibility against a default **70% threshold**.

4. **CA & Final Mark Predictor (`CaPredictorActivity`)**
   - Calculates the minimum score required in the final exam based on Continuous Assessment (CA %) to hit the target grade.

5. **Dashboard (`MainActivity`)**
   - Intuitive dashboard featuring quick-access cards for all tools and a full syllabus overview.

---

## ⚙️ Data Configuration & Customization

- **Syllabus Data:** Pre-populated with verified subjects and credit counts for Semesters 1 through 4 in `HNDITData.java`.
- **Attendance Rule:** Managed via the `ATTENDANCE_THRESHOLD` constant in `AttendanceActivity.java` (default set to `70%`).
- **CA Weightage:** Configured to a default **40% CA / 60% Final Exam** split. Weightages can be customized per subject inside the `HNDITData.java` constructor parameters.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (Electric Eel or newer recommended)
- JDK 11 or higher

### Installation & Setup
1. Clone or download this repository.
2. Open **Android Studio** -> **Open** -> Select the `HNDITAcademicToolkit` folder.
3. Allow Gradle to sync dependencies automatically.
4. Select the `app` module and click **Run** on an emulator or connected Android device.

---

## 📁 Package Structure

```text
com.hndit.academictoolkit
 ├── Subject.java                # Data Model for Subjects
 ├── HNDITData.java               # Syllabus & Subject Database
 ├── MainActivity.java           # Dashboard UI
 ├── GpaActivity.java            # GPA Calculator Logic
 ├── TargetGpaActivity.java      # CGPA Predictor Logic
 ├── AttendanceActivity.java     # Attendance Checker Logic
 └── CaPredictorActivity.java    # CA Mark Predictor Logic
