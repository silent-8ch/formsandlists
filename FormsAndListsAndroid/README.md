# Forms and Lists Android

## Project Rules

### String Resources
1. All user-facing strings MUST be defined in `res/values/strings.xml`
2. Never hardcode strings in layouts or code
3. String resource keys should be snake_case and descriptive
4. String resources should be categorized with comments when the list grows

### Testing
1. UI tests MUST verify against string resources, not hardcoded values
2. Use `InstrumentationRegistry.getInstrumentation().targetContext` to access resources in tests
3. Each UI component should have corresponding tests for:
   - Display text verification
   - User interaction
   - Error states
   - Success states

### Example
```kotlin
// Bad
Text("Hello")

// Good
Text(stringResource(R.string.greeting))

// Bad Test
assertEquals("Hello", someText)

// Good Test
val expected = context.getString(R.string.greeting)
assertEquals(expected, someText)
``` 