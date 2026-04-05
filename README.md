
<div align="center">
  <img src="/src/main/resources/assets/nerv/icon.png" alt="logo" width="20%"/>
  <h1>Nerv Printer — 8b8t Fork</h1>
  <p>
    A fork of the original Nerv Printer addon for Meteor Client,  
    optimized specifically for 8b8t.  
    Includes reliability improvements, new features, and workflow upgrades.
  </p>

  <!-- Shields -->
  <a href="https://github.com/Tobiasidk/nerv-printer-8b8t/releases">
    <img src="https://img.shields.io/github/v/release/Tobiasidk/nerv-printer-8b8t" />
  </a>
  <a href="https://github.com/Tobiasidk/nerv-printer-8b8t/commits">
    <img src="https://img.shields.io/github/last-commit/Tobiasidk/nerv-printer-8b8t" />
  </a>
  <a href="https://github.com/Tobiasidk/nerv-printer-8b8t/issues">
    <img src="https://img.shields.io/github/issues/Tobiasidk/nerv-printer-8b8t" />
  </a>
  <a href="https://github.com/Tobiasidk/nerv-printer-8b8t/stargazers">
    <img src="https://img.shields.io/github/stars/Tobiasidk/nerv-printer-8b8t" />
  </a>
</div>

---

## About This Fork

This fork of **Nerv Printer** focuses on **8b8t compatibility**, **mapart production time**.  
It keeps full compatibility with the original workflow while introducing numerous improvements that make printing faster, safer, and more reliable on high‑lag (because that's most of the time on 8b8t).

These improvements make Piston-Clearable maps as easy to make as Carpet maps, and Flat Fullblock maps are equally as possible to make with minimal setting changes.

This fork is fully compatible with:
- 8b8t
- Any server with `/home` + `/back` systems

---

## Improvements in This Fork

### **Better Error Detection & Verification**
- Missing blocks caused by lag/ghost blocks are now detected as errors.
- Master performs a **full map verification** before finishing, preventing corrupted prints.

### **Multi‑PC Mode**
Allows two players on different computers to share the same printing platform and workflow.  
(Current implementation works reliably; will be refined later.)

### **Deterministic Slave Ordering**
Slaves always work on the same slice, improving predictability and allowing fixed `/home` start positions.

### **Budget Auto‑Replenish**
A custom replenisher that:
- Works better than Meteor’s built‑in one  
- Outperforms IPN  
- Avoids unnecessary swaps and inventory thrashing

### **Optimal Hotbar Swapping**
A mathematically optimal swap algorithm:
- Predicts future block usage  
- Minimizes total swaps  
- Reduces wasted time  
- Works for both Carpet and Staircased printers  
- Includes a generic T‑cell extractor for Staircased compatibility (the extractor was vibecoded)

### **Shulker‑Compatible Restocking**
Restock logic now supports:
- Shulker unloaders  
- Mixed chest/shulker systems  
- Complex sorter setups

### **Safe Block Interactions**
Ensures the bot **always interacts with an empty hand**, preventing accidental block placement inside:
- Shulker unloaders  
- Sorters  
- Redstone components

### **8bUtils Module**
A small utility module tailored for 8b8t.  
(One of its utilities will be integrated into SlaveSystem later.)

### **Full 8b8t `/home` System Integration**
Bots automatically navigate using:
- `/home start`
- `/home dump`
- `/home hub`
- `/home middle`
- `/back`

Works flawlessly on 8b8t; may require adjustments for other servers.

### **Automatic Map Renaming**
Uses `/rename` to rename finished maps automatically.

### **BlockUtilsMixin Improvements**
Prevents placing blocks on:
- Replaceable blocks (resin clumps, roots, etc.)
- Multi‑count blocks (candles, wildflowers)
Improves compatibility with piston‑clearable maps.

### **Autojump for Piston‑Clearable Maps**
Adds a custom autojump layer (requires vanilla autojump enabled).

### **Staircased Printer Compatibility Fixes**
Minimal but necessary changes to ensure full compatibility with the new systems.

---

## Original Documentation

All original Nerv Printer documentation still applies:

- **Carpet Printer** — [Documentation/CarpetGuide.md](Documentation/CarpetGuide.md)  
- **Staircased Printer** — [Documentation/StaircasedGuide.md](Documentation/StaircasedGuide.md)  
- **Fullblock Printer (deprecated)** — [Documentation/FullblockGuide.md](Documentation/FullblockGuide.md)

## Credits

All credit for the original Nerv Printer goes to **Julflips** and contributors.  
This fork builds on their work to provide a more robust experience for 8b8t and similar servers.


