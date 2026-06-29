# Apache Sling Distribution Journal Messages

## Overview

This bundle defines the wire-format message types used by the journal-based Sling Content Distribution system. It also defines the `MessagingProvider` SPI — the abstraction through which the distribution-journal bundle sends and receives messages without depending on a specific broker. All message POJOs (e.g., `PackageMessage`, `DiscoveryMessage`, `PackageStatusMessage`) live here, along with the `MessageHandler`, `MessageSender`, and `MessageInfo` interfaces that any broker adapter must implement.

## Tech Stack

- Java 11, OSGi bundle (`sling-bundle-parent` v66)
- Lombok 1.18 for boilerplate reduction on message POJOs
- Testing: JUnit 4, Hamcrest, Logback, Jackson (for JSON round-trip tests)

## Build & Test Commands

```
mvn clean install            # build and run tests
mvn test -Dtest=PackageMessageTest   # single test class
```

## Architecture

Two top-level packages:

**`org.apache.sling.distribution.journal`** — core SPI:
- `MessagingProvider` — factory for `MessageSender` and `Closeable` subscribers
- `MessageSender<T>` — sends typed messages to a topic
- `MessageHandler<T>` — callback interface for received messages
- `MessageInfo` — metadata (topic, partition, offset, timestamp) carried with each received message
- `JournalAvailable` — OSGi condition service indicating the journal backend is up
- `BinaryStore` — SPI for storing large binary payloads outside the journal

**`org.apache.sling.distribution.journal.messages`** — concrete message types:
- `PackageMessage` — distribution package (content paths, package type, binary data or blob reference)
- `PackageStatusMessage` — subscriber import outcome (IMPORTED, REMOVED, FAILED)
- `DiscoveryMessage` — subscriber heartbeat, carries last-processed offset
- `PackageDistributedMessage` — fired after successful distribution (published as OSGi event)
- `LogMessage`, `OffsetMessage`, `PingMessage`, `ClearCommand`, `SubscriberConfig`, `SubscriberState`

Message types use Lombok `@Builder` / `@Value` annotations. The annotation processor must be active during compilation; this is handled via the `maven-compiler-plugin` `annotationProcessorPaths` configuration.

## Conventions & Gotchas

- **No broker dependency**: this bundle is intentionally broker-agnostic. The actual Kafka (or other) adapter that implements `MessagingProvider` is a separate bundle deployed alongside. Do not add Kafka/Pulsar imports here.
- Lombok processes annotations at compile time. If your IDE doesn't recognise generated methods, enable annotation processing in the IDE settings and import the Lombok plugin.
- Message backwards compatibility matters: this bundle's types are serialized over the wire and may be consumed by instances running older versions. Adding fields with Lombok `@Builder.Default` is safe; removing or renaming fields is a breaking change requiring a version bump.
- `PackageMessage` has two content modes: inline bytes (`pkgBinary`) for small packages and a blob reference (`pkgBinaryRef`) for large packages stored in the shared Oak blob store. Consumers must handle both.
- The `serialized.json` and `serialized-no-metadata.json` test resources exist to verify backwards-compatible deserialization — do not delete them.
