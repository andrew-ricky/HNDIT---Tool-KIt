# HNDIT Academic Toolkit (Android - Java & XML)

ATI Galle HNDIT syllabus එකට ගැලපෙන Native Android App එකක්. Backend/Firebase නෑ —
subject data ටික `HNDITData.java` file එකේ hardcode කරලා තියෙන්නේ.

## ඔයා දුන්න Data වලට කරපු වෙනස්කම්

- **Subjects**: Semester 1-4 subject list එක ඔයා දුන්න නිවැරදි syllabus එකට අනුව
  update කරලා තියෙනවා (internet එකෙන් හොයපු data නෙවෙයි).
- **Attendance Rule**: Hours නෙවෙයි, **Lecture Days** ගානට. Threshold එකත් 80% නෙවෙයි
  **70%**ට (`AttendanceActivity.java` file එකේ top එකේ `ATTENDANCE_THRESHOLD` constant
  එක තියෙන්නේ, ඕන නම් පස්සේ පහසුවෙන් වෙනස් කරගන්න පුළුවන්).
- **CA Weightage**: Subject එකින් එකට ලකුණු බෙදීම (CA %) ඔයා දීලා තිබුනේ නෑ. ඒ
  නිසා default විදිහට හැම subject එකකටම **CA 40% / Final Exam 60%** කියලා තියෙන්නේ.
  ඔයාගේ lecturer කියපු actual % එක Subject එකට වෙනස් නම්, `HNDITData.java` file එකේ
  ඒ subject එකේ `new Subject(...)` constructor එකේ last number එක (caWeightage) වෙනස්
  කරන්න.

## Modules (5)

1. `GpaActivity` — Semester GPA Calculator (Grade "N/A" කියලා Select කරොත් ඒ subject එක
   calculation එකෙන් ඉවත් වෙනවා — Elective එකක් ගත්තේ නැති අයට use කරන්න පුළුවන්).
2. `TargetGpaActivity` — Target CGPA Predictor (Total Program Credits = 56 කියලා auto-calculate
   කරනවා, ඕන නම් Remaining Credits manually දාන්නත් පුළුවන්).
3. `AttendanceActivity` — Days-based, 70% Eligibility Checker.
4. `CaPredictorActivity` — CA % + Target Grade එකෙන් ඕන Final Exam Mark එක calculate කරනවා.
5. `MainActivity` — Dashboard (4 CardViews + Syllabus overview).

## Android Studio එකට දාගන්නේ කොහොමද?

1. Android Studio → **Open** → මේ folder එක (`HNDITAcademicToolkit`) select කරන්න.
   (මේක දැනටමත් සම්පූර්ණ Gradle Project structure එකක් - "Empty Views Activity" කියලා
   අලුතෙන් හදන්න ඕන නෑ.)
2. Gradle Sync වෙනකන් ඉන්න (පළවෙනි වතාවට Internet ඕන Dependencies download කරගන්න).
3. `app` module එක Run කරන්න (▶ button).
4. App Icon (`ic_launcher`) හදලා නෑ — Android Studio එකෙන්ම default icon එකක් auto-assign
   වෙයි, ඕන නම් **File → New → Image Asset** එකෙන් ATI Galle logo එකක් දාගන්න පුළුවන්.

## Package Structure

```
com.hndit.academictoolkit
 ├── Subject.java
 ├── HNDITData.java
 ├── MainActivity.java
 ├── GpaActivity.java
 ├── TargetGpaActivity.java
 ├── AttendanceActivity.java
 └── CaPredictorActivity.java
```
