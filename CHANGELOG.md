
## [1.0.1] - 2025-06-09
My thinkpad died while trying to fight a frozen main thread of the game.
The lag won, so I rewrote this mod's code so a backup procedure occurs in a separate thread.
The main thread will just silently wait and track progress.

- Added support for ".lang" files
- Added Russian language translation to the mod
- Now a manual backup occurs in off-thread to prevent the main thread from hanging and freezing
- Widened the buttons in a world manager GUI
- Update toolchain to FoxLoader 2.0-alpha14

## [1.0] - 2025-06-08
- Initial release