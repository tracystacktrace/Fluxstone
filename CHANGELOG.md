
## [1.0.3] - 2025-06-30

- Added server bookmarking! Now you can bookmark and manage inside your servers list!
- The server list GUI got some redesigning
- Fixed some micro bugs related to world bookmarking
- Updated the toolchain to work on `FoxLoader 2.0-alpha22`.

## [1.0.2] - 2025-06-09
Very tiny changes, but this is the first version to be automatically assembled in GitHub Actions
<br>
No need to compile and publish stuff by myself. Hooray!

- Now "World Modification" is set to false by default. You need to enable it in configs so you could toggle cheats/creative options
- Added hot gay pink as a second in-built gradient for bookmarks

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