# ZambieLib

Shared internals for my mods (DiscordPresence, Wildlore, Broadback). Does nothing on its own; install it because another mod asked for it.

What lives here:

- `client/GuiKit` - vanilla-style panel, inset, scrollbar and progress bar drawing
- `util/JsonStore` - JSON save/load with atomic writes and a .bak fallback
- `util/Boxes` - bounding box math (volume, overlap, top surfaces)
- `text/Placeholders` - `%key%` expansion for user-editable text

NeoForge 1.21.1. Build with `gradle build`, publish for sibling projects with `gradle publishToMavenLocal`.
