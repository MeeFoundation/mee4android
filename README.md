<div align="center">
  <br>
    <img src="readme_logo.svg" width="200px">
    <h1>Mee Foundation</h1>
</div>
<br>

We’re about giving people control of their own personal data by developing the Mee Smartwallet and fostering a community of compatible digital service providers.
## What is the Mee Smartwallet

The Mee Smartwallet is an authentication app that offers privacy and convenience by giving the user more control over their own personal information as they interact with websites, mobile apps, and other user's agents. Mee Smartwallet helps users to verify their identities before granting them access to websites without a password and without surveillance.

The Mee Smartwallet runs on the user's mobile phone where it holds, entirely under the user's control, a local, private database where the user's personal information is stored. When an app/site wants to know something about the user, the Mee Smartwallet shares as much or as little as the user prefers.

You can find more at our [Docs](https://docs-dev.mee.foundation/)
## Table of Contents
- [Contributing](#contributing)
- [How does it work](#how-does-it-work)
- [Getting Started](#getting-started)
    - [Installation Documentation](#installation-documentation)
    - [Project structure](#project-structure)
- [License](#license)

## Contributing

We encourage you to contribute to the Mee Smartwallet! Please check out the [Contribution guide](https://docs-dev.mee.foundation/contributing/roadmap/) for guidelines about how to proceed.

## How does it work

Any user who installed the Mee Smartwallet app can sign in or sign up to websites and apps that support the OpenID Connect SIOPv2 protocol standard.

[SIOPv2](https://openid.net/specs/openid-4-verifiable-presentations-1_0.html) is an OpenID specification to allow end-users to act as OpenID Providers (OPs) themselves. Using self-issued OPs, end-users can authenticate themselves and present claims directly to a Relying Party (RP).
* RP is an app/site that requires end-user authentication.
* End user is an entity for whom identity info is requested.
* Holder is an entity that receives credentials and has control over them to present them to the RP.

In our case, the RPs are partner websites (such as [mee.foundation](https://mee.foundation)). Holder is the Mee Smartwallet app.

When the user clicks "Connect with Mee" on a partner website, OIDC Request data is passed to the Mee Smartwallet app and a user interface generated, requesting the data claims required.

When the user approves the requested data to be shared, the Mee Smartwallet generates an [OIDC Response](https://openid.net/specs/openid-connect-core-1_0.html#SelfIssuedResponse), encrypts and signs the data and then passes it back to the Relying Party.

Please see the [Docs](https://docs-dev.mee.foundation/integration/connect-with-mee) for more information.

## Getting Started

This section provides a quick start guide and and an overview of the project structure.

### Installation Documentation
1) Fork repository
2) Clone your forked repository
3) Initialize submodules
```
git submodule init
git submodule update
```
`.gitmodules` refers to `mee-core` through the SSH alias host `swift.github.com`. If you don't have that alias in your `~/.ssh/config`, point the submodule at GitHub directly (local override, no changes to `.gitmodules`):
```
git config submodule.mee-core.url git@github.com:MeeFoundation/mee-core.git
git submodule update --init mee-core
```
4) Clone the two Rust crates that `mee-core` uses as path dependencies. They must be siblings of `mee-core`, i.e. in the repository root:
```
git clone -b did_key_jwk_jcs_pub git@github.com:MeeFoundation/ssi.git ssi
git clone git@github.com:MeeFoundation/didkit.git didkit
```
The `did_key_jwk_jcs_pub` branch of `ssi` is required: `main` has no `DIDKey::generate_jwk_jcs_pub`, which `mee_did` calls.

Steps for **Mac OS**:
1)  Install [Rust](https://www.rust-lang.org/learn/get-started)
2) Use the Rust `1.76.0` toolchain for `mee-core`. Compilers newer than 1.79 fail on the pinned `time 0.3.20` crate (`error[E0282]`), and cargo 1.78+ rewrites `mee-core/Cargo.lock` into lockfile format v4:
```
rustup toolchain install 1.76.0
cd mee-core && rustup override set 1.76.0
```
3) Install Rust targets:
```
rustup target add --toolchain 1.76.0 aarch64-linux-android
rustup target add --toolchain 1.76.0 x86_64-linux-android
```
4) Compile **uniffi-bindgen**:
```
cd mee-core
cargo build --bin mee_uniffi_bindgen
```
5) Install **NDK** version `25.2.9519653` (pinned as `ndkVersion` in [app/build.gradle](app/build.gradle))
6) Export `RUST_ANDROID_GRADLE_PYTHON_COMMAND` variable. It has to point to a Python **older than 3.13** — `linker-wrapper.py` from the `rust-android-gradle` plugin imports the `pipes` module, which was removed in 3.13. On macOS the bundled interpreter works:
```
export RUST_ANDROID_GRADLE_PYTHON_COMMAND=/usr/bin/python3
```
7) Build the app and install it on a connected device or a running emulator:
```
./gradlew installDebug
```

### Project structure

At Mee Smartwallet, we use **Kotlin** for development and **Gradle** as our build tool.

The overall project structure is standard, including a [main](app/src/main) directory containing:
- [AndroidManifest.xml](app/src/main/AndroidManifest.xml)
- `Kotlin code sources` in a [foundation/mee/android_client/](app/src/main/java/foundation/mee/android_client)  
- `application resources`, such as drawable files in a [res/](app/src/main/res) directory

The Kotlin code sources overview:
- [controller/biometry/](app/src/main/java/foundation/mee/android_client/controller/biometry) lock screen authentication logic
- [effects/](app/src/main/java/foundation/mee/android_client/effects) side-effects, i.e. related to the lifecycle
- [models/](app/src/main/java/foundation/mee/android_client/models) data models and the utils related
- [navigation/](app/src/main/java/foundation/mee/android_client/navigation) everything related to the app navigation
- [service/](app/src/main/java/foundation/mee/android_client/service) web services and clients (i.e. InstallReferrerClient)
- [ui/](app/src/main/java/foundation/mee/android_client/ui) shared UI components (i.e. buttons)
- [utils/](app/src/main/java/foundation/mee/android_client/utils) set of common helpers
- [views/](app/src/main/java/foundation/mee/android_client/views) application views
- [MainActivity.kt](app/src/main/java/foundation/mee/android_client/MainActivity.kt) 
- [MainApplication.kt](app/src/main/java/foundation/mee/android_client/MainApplication.kt)
- [MeeAgentViewModel.kt](app/src/main/java/foundation/mee/android_client/MeeAgentViewModel.kt)

The `mee-core` submodule is located in the project's root directory.`mee-core` provides the MeeAgent class which allows us to interact with other parties’ apps, websites and/or agents. The core logic is implemented in the [MeeAgentStore](app/src/main/java/foundation/mee/android_client/models/MeeAgentStore.kt) class where we use MeeAgent.

Please see the [Docs](https://docs-dev.mee.foundation/basics/terminology) for more information.
    
## License

Apache License 2.0 [Mee Smartwallet](/LICENSE) 
