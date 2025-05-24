# Cursor Project Rules

## String Management
1. All UI strings must be in `strings.xml`
2. No hardcoded strings in code
3. String keys: snake_case
4. String usage: `stringResource()` in Compose, `getString()` in Android Views

## Testing
1. Test against resource strings, not hardcoded values
2. Each UI component needs:
   - Text verification
   - Interaction tests
   - State tests

## Code Style
1. Kotlin only
2. Compose for UI
3. No deprecated Android APIs
4. Single responsibility principle
5. Max function length: 30 lines
6. Max file length: 300 lines

## Project Structure
```
app/
  src/
    main/
      java/
        com.p4u1.formsandlists/
          ui/           # UI components
          data/         # Data handling
          util/         # Utilities
      res/
        values/
          strings.xml   # ALL strings here
    test/              # Unit tests
    androidTest/       # UI tests
```

## Commit Messages
1. Prefix with type:
   - feat: new feature
   - fix: bug fix
   - refactor: code improvement
   - test: test changes
2. Keep under 50 chars
3. Use imperative mood

## Dependencies
1. Prefer Android standard library
2. Avoid external dependencies
3. Document required dependencies 