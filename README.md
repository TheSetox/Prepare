
<img src="/image/preparation_icon.png" width="200">
<a href="https://www.flaticon.com/free-icons/prepare" title="prepare icons">Prepare icons created by kerismaker - Flaticon</a>

# Prepare

A lightweight utility composable for preparing and loading individual composables.

```kotlin
@Composable
fun Prepare(
    preview: () -> Unit = {},
    data: @Composable () -> Unit = {},
    dialog: @Composable () -> Unit = {},
    screen: @Composable () -> Unit,
)
```

## 1. Add Custom/Github Repository

Add this in your repositories. Either in `settings.gradle` or `build.gradle`. (Depends on your gradle version)

**Groovy**

```gradle
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TheSetox/Prepare")
            def localProperties = new Properties()
            try {
                new FileInputStream("local.properties").withStream { fileInputStream ->
                    localProperties.load(fileInputStream)
                }
            } catch (FileNotFoundException ignored) {
                // If local.properties file is not found, log a message and continue
                println("local.properties file not found, using system environment variables.")
            }
            def user = localProperties.getProperty("gpr.user") ?: System.getenv("USERNAME")
            def token = localProperties.getProperty("gpr.key") ?: System.getenv("TOKEN")
            credentials {
                username = user ?: System.getenv("USERNAME")
                password = token ?: System.getenv("TOKEN")
            }
        }
    }
```

**Kotlin**
```gradle
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TheSetox/Prepare")
            val prop = Properties().apply {
                load(FileInputStream(File(rootProject.projectDir, "local.properties")))
            }
            val user = prop.getProperty("gpr.user")
            val token = prop.getProperty("gpr.key")
            credentials {
                username = user ?: System.getenv("USERNAME")
                password = token ?: System.getenv("TOKEN")
            }
        }
    }
```

## 2. Add dependencies in app-level

### For Android:

**Kotlin**
```gradle

implementation("com.thesetox.prepare:core:1.0.0")
```

**Groovy**
```gradle
implementation 'com.thesetox.prepare:core:1.0.0'
```


**PS: You can also use the dependency created in multiplatform.**

```gradle
implementation("com.thesetox.prepare:multiplatform-android:1.0.0")
```

### For Kotlin Multiplatform:

**Supported**: iOS, jvm, and android.

**Not Supported**: wasm, jvm, macos (**Under consideration**)

 **Kotlin**
```gradle

implementation("com.thesetox.prepare:multiplatform:1.0.0")
```

**Groovy**
```gradle
implementation 'com.thesetox.prepare:multiplatform:1.0.0'
```

**PS: You can also use the individual dependencies located in https://github.com/TheSetox?tab=packages&repo_name=Prepare.**

## Purpose

The purpose of this utility library is to remove boilerplate code on managing `@Preview` and your main `@Composable`.

For example, When creating a `@Composable` Screen, you just need to create the following:

```kotlin
@Composable
fun Screen() {
  // Other composable code
}
```

To preview, you just need to add the `@Preview`

```kotlin
@Preview
@Composable
fun ScreenPreview() {
  Screen()
}
```

This is great. You can see your UI without running your app.

`@Preview` is a **powerful tool**. 

It does not only view your UI in screen-level and view individual UI components 
but you can also configure it to see it in different _screen types_, _configuration_ and `states`. 

Example of states of a screen: **Loading**, **Error**, **Success** and **etc**.

Basically, different scenarios that can happen in the UI.

### Problem 

The problem is when we need or depend on a `ViewModel` in our `@Composable`.

The `@Preview` will **not render** because it does not know how to handle the `ViewModel`.

Ex.

```kotlin
@Composable
fun Screen(viewModel: ViewModel = hiltViewModel())
```

```kotlin
@Preview
@Composable
fun ScreenPreview() {
  Screen() <--- can't render because hiltViewModel don't know how to inject.
}
```

Some may argue that addressing this issue is not a top priority, as the `@Preview` functionality isn't directly related to the user-facing UI. 
While this perspective holds merit, it's crucial not to underestimate the power of `@Preview`.

By neglecting to address compatibility with `@Preview`, you risk overlooking a valuable tool for rapidly iterating and testing various states of your application's UI. 
Without proper integration, you may find yourself spending excessive time repeatedly running your app to assess different states manually.

Therefore, while `@Preview` may not directly impact the end-user experience, its significance in expediting the development process and ensuring UI robustness should not be understated.

### Possible Solution

There are different ways to resolve the problem.

**1. Make an `interface` for `ViewModel` and create a mock `ViewModel` so you can pass the mock `ViewModel` in the `@Preview` Screen.**

```kotlin
@Preview
@Composable
fun ScreenPreview() {
  val viewModel = mockViewModel() <-- This has static values.
  Screen(viewModel)
}
```


**2. You can just pass the `state` instead of the `ViewModel`.**

```kotlin
@Composable
fun Screen(state: State<String>)
```


**3. You can also create the same `@Composable` name. One who accepts the `ViewModel` and the second one accepts the `state` from the first `@Composable` function.**
```kotlin
@Composable
fun Screen(viewModel: ViewModel = hiltViewModel()) {
  val state = viewModel.state.collectAsState()
  Screen(state)
}

@Composable
fun Screen(state: State<String>) {
  // Add you main logic
}

@Preview
@Composable
fun ScreenPreview() {
  val state = remember { mutableStateOf("Preview") }
  Screen(state)
}
```


**4. Create a `ProvidableCompositionLocal` that will be used to check when we call the `@Composable Screen()`.
We only set it to `true` when we run the `@Preview` composable. So we don't need to initialize the `ViewModel`
when in `@Preview` mode.**

```kotlin
private val LocalPreviewMode: ProvidableCompositionLocal<Boolean> = compositionLocalOf { false }

@Composable
fun Screen() {
  val state = remember { mutableStateOf("Default Value") }
  if (LocalPreviewMode.current.not()) {
    val viewModel: ViewModel = hiltViewModel()
    state = viewModel.state.collectAsState()
  }
  Content(state)
}

@Preview
@Composable
fun ScreenPreview() {
  CompositionLocalProvider(LocalPreviewMode provides true) {
    Screen()
  }
}
```

### Conclusion

These are great solution. But the only problem with this is that we added additional **Boilerplate** code
just to handle a `@Preview`.

There is more boilerplate code in 1 and 3. For 2, is debatable. The best solution is 4. But it is still a bit unpleasing to check
`LocalPreviewMode.current` in the every `Screen` and update the `LocalPreviewMode` to true in `@Preview`.

### Prepare Lib Solution

This is what **Prepare** tries to help. We integrate the same implementation in number 4. So that
you won't need to worry about it. Just add `Prepare` and add `PreparePreview` in `@Preview`.
A simple solution to an underrated problem.

```kotlin
@Composable
fun Screen() {
  val state = remember { mutableStateOf("") }
  Prepare(
    preview = { state = remember { mutableStateOf("Preview Mode") } },
    data = {
      val viewModel: ViewModel = hiltViewModel()
      state = viewModel.state.collectAsState()
    },
    screen = { Content(state) },
  )
}

@Preview
@Composable
fun ScreenPreview() {
  PreparePreview {
    Screen()
  }
}
```

### Learn more

You can check the [sample project](https://github.com/TheSetox/Prepare/tree/main/sample) in this repository for more concrete examples.
