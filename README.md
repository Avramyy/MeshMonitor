# Mesh Monitor 🚀

تطبيق Android لمراقبة وإعادة تشغيل تطبيق **MeshCentral Agent** تلقائياً.

## الميزات ✨

- ✅ مراقبة مستمرة لـ MeshCentral Agent
- ✅ فحص تلقائي كل دقيقة واحدة
- ✅ إعادة تشغيل تلقائية عند التوقف
- ✅ تصغير التطبيق بعد التشغيل
- ✅ إشعارات عند إعادة التشغيل
- ✅ يعمل في الخلفية (Foreground Service)
- ✅ بدء تلقائي عند تشغيل الجهاز
- ✅ واجهة رسومية سهلة (Jetpack Compose)

## المتطلبات 📋

- Android 8.0 (API 26) فما فوق
- تطبيق MeshCentral Agent مثبت على الجهاز

## التثبيت 📥

### الطريقة 1: عبر Android Studio

1. **Clone المشروع:**
```bash
git clone https://github.com/Avramyy/MeshMonitor.git
cd MeshMonitor
```

2. **افتح المشروع في Android Studio**

3. **بناء التطبيق:**
```bash
./gradlew build
```

4. **تشغيل على الجهاز:**
```bash
./gradlew installDebug
```

### الطريقة 2: من خلال APK

1. اضغط على **Releases**
2. حمّل آخر APK
3. ثبّت على جهازك

## الاستخدام 🚀

1. **افتح تطبيق Mesh Monitor**
2. **اضغط زر "Start" لبدء المراقبة**
3. **التطبيق سيفحص MeshCentral Agent كل 60 ثانية**
4. **إذا توقف سيعاد تشغيله تلقائياً مباشرة**
5. **سترى إشعار عند إعادة التشغيل**

## البنية 📁

```
MeshMonitor/
├── src/main/
│   ├── kotlin/com/meshmonitor/
│   │   ├── MainActivity.kt              # واجهة المستخدم (Compose)
│   │   ├── MeshMonitorService.kt        # خدمة المراقبة الرئيسية
│   │   └── BootReceiver.kt              # البدء التلقائي عند التشغيل
│   └── AndroidManifest.xml              # إعدادات التطبيق
├── build.gradle.kts                     # إعدادات البناء
└── README.md                            # هذا الملف
```

## الأذونات المطلوبة 🔐

| الإذن | الغرض |
|------|-------|
| `QUERY_ALL_PACKAGES` | للتحقق من التطبيقات المثبتة |
| `FOREGROUND_SERVICE` | للعمل في الخلفية |
| `POST_NOTIFICATIONS` | لإرسال الإشعارات |
| `RECEIVE_BOOT_COMPLETED` | للبدء التلقائي عند التشغيل |

## كيفية المراقبة 🔍

التطبيق يقوم بـ:

1. **فحص** ما إذا كان `com.meshcentral.agent` قيد التشغيل
2. **إذا لم يكن يعمل** → يعاد تشغيله تلقائياً
3. **بعد إعادة التشغيل** → يصغر التطبيق
4. **يرسل إشعار** بـ "تم إعادة تشغيل MeshCentral Agent"
5. **ينتظر 60 ثانية** ثم يفحص مرة أخرى

## إعدادات التطبيق ⚙️

### تغيير فترة الفحص

لتغيير المدة من 60 ثانية إلى مدة أخرى:

افتح `MeshMonitorService.kt` وغيّر:
```kotlin
private const val CHECK_INTERVAL = 60000L // بالميلي ثانية
```

مثال:
- 30 ثانية: `30000L`
- دقيقة: `60000L`
- دقيقتين: `120000L`

### تغيير التطبيق المراقب

افتح `MeshMonitorService.kt` وغيّر:
```kotlin
private const val PACKAGE_NAME = "com.meshcentral.agent"
```

## استكشاف الأخطاء 🛠️

### المشكلة: التطبيق لا يبدأ تلقائياً عند الإقلاع

**الحل:**
1. تأكد من السماح بـ "Modify system settings"
2. أضف التطبيق للقائمة البيضاء في battery optimization
3. تحقق من الأذونات في إعدادات الجهاز

### المشكلة: لا يتم إعادة تشغيل MeshCentral Agent

**الحل:**
1. تأكد من تثبيت MeshCentral Agent
2. تحقق من سماح التطبيق بالوصول للتطبيقات الأخرى
3. شغّل على جهاز بـ API 26+

### المشكلة: لا تظهر الإشعارات

**الحل:**
1. تحقق من إعدادات الإشعارات للتطبيق
2. تأكد من السماح بـ `POST_NOTIFICATIONS`

## قائمة التحقق ✓

- [x] مراقبة كل دقيقة
- [x] إعادة تشغيل تلقائية
- [x] Minimize بعد التشغيل
- [x] Foreground Service (يعمل بالخلفية)
- [x] إشعارات
- [x] بدء تلقائي عند الإقلاع
- [x] واجهة رسومية (Jetpack Compose)
- [x] دعم Kotlin
- [x] Material Design 3

## النسخة 📦

**الإصدار:** 1.0
**حالة الدعم:** ✅ قيد التطوير

## المساهمة 🤝

هل تريد تحسين التطبيق؟ يمكنك:
1. Fork المشروع
2. إنشاء branch جديد (`git checkout -b feature/myfeature`)
3. Commit التغييرات (`git commit -m 'Add myfeature'`)
4. Push الـ branch (`git push origin feature/myfeature`)
5. فتح Pull Request

## الدعم 💬

في حالة وجود أي مشاكل، تأكد من:
- ✓ تطبيق MeshCentral Agent مثبت ومحدّث
- ✓ الأذونات مفعلة جميعها
- ✓ Mesh Monitor لديه إذن البقاء في الخلفية
- ✓ جهازك يعمل بـ Android 8.0 أو أحدث

## الترخيص 📄

MIT License

```
Copyright 2024 Avramyy

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

**تم الإنشاء بواسطة:** [Avramyy](https://github.com/Avramyy) 🚀

**آخر تحديث:** 2024
