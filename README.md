# cc-wc

A lightweight, high-performance Java clone of the classic Unix `wc` (word count) tool. Built as a hands-on learning project for John Crickett's [Coding Challenges](https://codingchallenges.fyi/challenges/challenge-wc/).

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](#)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Language](https://img.shields.io/badge/Language-Java%2017%2B-orange.svg)](#)
[![Challenge](https://img.shields.io/badge/Challenge-codingchallenges.fyi-blue.svg)](https://codingchallenges.fyi/challenges/challenge-wc/)

---
## Why This Project?

Most modern programs read text files by immediately turning raw bytes into characters and strings.

I built `cc-wc` to understand how low-level systems actually handle data under the hood. By streaming data through a custom **64KB memory buffer** and using **bitwise operations** to decode tricky UTF-8 characters on the fly, this tool processes files quickly and efficiently without unnecessary memory allocations.

---
## How It Works

Instead of using high-level readers that auto-decode text, this tool reads raw bytes directly into a manual 64KB array using `BufferedInputStream`. 

```mermaid
graph TD
    A[File or Piped Input] --> B[BufferedInputStream 64KB Buffer]
    B --> C{Which Flag?}
    C -->|-c| D[Count Raw Bytes]
    C -->|-l| E[Count Newlines \n]
    C -->|-w| F[Count Word Boundaries]
    C -->|-m| G[Bitmask UTF-8 Characters]
    C -->|None| H[Run Default Line/Word/Byte Mode]
    
    D & E & F & G & H --> I[Print to Console]
````

- **UTF-8 Character Tracking (`-m`):** UTF-8 characters can be anywhere from 1 to 4 bytes long. To count actual characters instead of just raw bytes, the engine looks at the first few bits of a byte using bitwise `AND` operators to figure out exactly how long that character is.

## Quick Start

### Prerequisites

- **Java Development Kit (JDK):** Version 17 or higher.
- **Build Tool:** Standard native Java compilation environment (`javac`).

### Building the Project

Clone the repository and compile the source code directly:

```bash
git clone https://github.com/printf1973/cc-wc.git
cd cc-wc
javac ccwc.java
```

### Try It Out

**1. Using a file path:**

```bash
# Count lines in a file
java ccwc -l test.txt

# Count bytes in a file
java ccwc -c test.txt
```

**2. Piping input from another command:**

```bash
# If you don't pass a flag, it defaults to counting lines, words, and bytes at once
cat test.txt | java ccwc
```

## What It Handles

|**Flag**|**What it counts**|**How it works underneath**|
|---|---|---|
|`-c`|**Bytes**|Counts the exact number of raw bytes returned from the stream buffer.|
|`-l`|**Lines**|Scans the byte array specifically for newline (`\n`) markers.|
|`-w`|**Words**|Acts as a small state machine, tracking transitions between spaces and text.|
|`-m`|**Characters**|Inspects bits to correctly count multi-byte UTF-8 symbols as single characters.|
|_None_|**Default**|Runs a combined pipeline that calculates Lines, Words, and Bytes in one pass.|

> [!NOTE]
> 
> This tool is built to evaluate one specific task at a time. Bundling multiple individual flags together into a single argument (like `-lwc`) is not supported.
