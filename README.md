# mee4android
iOS Mee client

## Prerequisites

Steps for Mac OS
1) Install Rust https://www.rust-lang.org/learn/get-started
2) Use Rust latest stable toolchain
3) Install Rust targets
```
rustup target add aarch64-linux-android
```
4) Install uniffi-bindgen
```
cargo install uniffi_bindgen
```
5) export variable
```
export RUST_ANDROID_GRADLE_PYTHON_COMMAND=python3
```

