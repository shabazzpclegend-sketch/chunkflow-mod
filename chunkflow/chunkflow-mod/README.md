# ChunkFlow

A Fabric mod for Minecraft 1.21.1, publicly framed as a chunk-loading /
hostile-entity performance mod. Under the hood it's the first piece of an
ARG.

## What it does right now

- **No hostile mobs.** Cancels spawns for the vanilla `MONSTER` spawn
  group (zombies, skeletons, creepers, spiders, endermen, witches, etc).
  Framed publicly as "reduces hostile-entity overhead" — which, as a side
  effect, is even true.
- **Cave glitch.** Once a second, checks each online player: if they're
  below Y=40 with no sky visible, that chunk gets an oversized, mismatched
  cluster of tree columns (15–40 blocks tall, occasional sideways ones,
  ragged floating leaf caps) dropped around them. Each chunk only glitches
  once — an invisible `STRUCTURE_VOID` marker block remembers which chunks
  have already fired, so it survives server restarts with no custom
  save-data code needed.
- **Cipher sign.** Right after the glitch, a sign goes up nearby with a
  two-line Caesar cipher (shift 7). The plaintext lives only in
  `CipherSignPlacer.java` on your machine — only the ciphertext is ever
  written into the world.

## Project layout

```
src/main/java/com/chunkflow/mod/
  ChunkFlowMod.java        — entrypoint, registers the two hooks
  CaveGlitchHandler.java   — tick loop + "is this player in a cave" check
  GlitchGenerator.java     — places the glitched tree columns
  CipherSignPlacer.java    — places the sign, encodes the clue
  CaesarCipher.java        — plain-Java encode/decode, no MC dependency
```

## Building it

I don't have network access in the environment I wrote this in, so I
couldn't pull the Fabric/Minecraft libraries to actually compile or run
this — you'll need to do that build step yourself. Two easy paths:

1. **IntelliJ IDEA** (recommended): install the Minecraft Development
   plugin, open this folder as a Gradle project, let it sync. It'll
   fetch everything and generate the Gradle wrapper for you.
2. **Command line**: install Gradle, run `gradle wrapper --gradle-version 8.8`
   once inside this folder, then use `./gradlew build` from then on. The
   built jar lands in `build/libs/`.

**Before building**, double-check `gradle.properties` — the Yarn mappings /
Fabric API / Loader versions pinned there were current for 1.21.1 as of my
last search, but Fabric ships updates often. If the build fails on
dependency resolution, check https://fabricmc.net/develop for current
numbers.

**If something doesn't compile:** the riskiest line in the code is the
`executeWithPrefix` call in `CipherSignPlacer.java` — I'm confident in the
approach (running a vanilla command through the server's console command
source) but less certain the exact method name hasn't shifted slightly
between Yarn builds. Your IDE's autocomplete on `world.getServer().getCommandManager().` will show the real name if it's changed.

To install: drop the built jar into your `mods` folder alongside Fabric
API and Fabric Loader.

## A couple of things worth knowing before you ship this anywhere

- If you plan to distribute this beyond people who already know what
  it is, be aware most mod hosting sites (Modrinth, CurseForge) have
  policies against listings that misrepresent what a mod actually does —
  worth a skim before uploading anywhere under the performance-mod framing.
- The mob-spawn block only targets the `MONSTER` spawn group — a few
  hostile-ish mobs in other categories won't be affected. Say if you want
  that tightened up.

## Not built yet

You mentioned a library full of real, readable books as the endpoint the
ciphers lead toward — that's a bigger single piece (structure generation +
custom book NBT), so I held off to keep this batch reviewable. Say the
word and I'll build that next, plus wire the cipher trail to actually
point at it.
