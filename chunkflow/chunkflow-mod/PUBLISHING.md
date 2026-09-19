# Publishing to Modrinth (unlisted)

Copy-paste-ready content for the project page, plus what to fill in on the
version upload. Written in the same "performance mod" voice as the rest of
the disguise — swap any of it out if you want a different tone.

## Project basics

- **Project type:** Mod
- **Name:** ChunkFlow
- **Summary** (short, shows in search/cards):
  Reduces hostile-entity overhead and streamlines chunk loading for
  smoother, more consistent frame times.
- **License:** MIT
- **Client/server side:** Required on both
- **Visibility:** Unlisted (set this under the project's visibility
  settings — it gets a real, stable link but won't show up in search or
  browsing, so it only reaches people you hand the link to)

## Long description (Markdown)

```markdown
# ChunkFlow

Lightweight chunk-loading and entity-overhead optimizations for smoother,
more consistent frame times.

## Features
- Trims hostile-entity tick overhead
- Streamlines chunk processing on load
- Zero config — works out of the box

## Compatibility
- Minecraft 1.21.1
- Fabric Loader 0.16.9+
- Requires Fabric API

## Installation
Drop the jar into your `mods` folder alongside Fabric API and Fabric Loader.
```

## Version upload fields

- **Version number:** 1.0.0
- **Loaders:** Fabric
- **Game versions:** 1.21.1
- **Release channel:** Release
- **Primary file:** the built jar from `build/libs/` (something like
  `chunkflow-1.0.0.jar`)
- **Changelog:** "Initial release."

## After publishing

Your project's URL (the one in your browser's address bar, or the "Share"
button on the page) is what you send to people. Since it's unlisted, that
link is the only way in — nobody finds it by browsing or searching
Modrinth.
