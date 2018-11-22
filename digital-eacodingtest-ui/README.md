# DigitalEacodingtestUi

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 7.0.5.

## Prerequisite

Both the Angular CLI and generated project have dependencies that require Node 8.9 or higher, together with NPM 5.5.1 or higher.

## Development server

### Proxying the backend services

Please refer to `proxy.config.json`. A proxy is made to the api at http://localhost:18080.

### Running the application

From `digital-eacodingtest-api` directory, run `./gradlew clean bootRun` to start the api (if not already) at http://localhost:18080/api/v1.   

From `digital-eacodingtest-ui` directory, run `npm i` and `npm run serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Build

Run `npm run build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `npm run test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Incomplete, and needs work!
