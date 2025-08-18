# 📝 messages.yml

## 📄 Introduction

The `messages.yml` file allows you to customize all messages that the **xTeams** plugin sends to players, helping create a tailored experience for your server. This file includes messages for team creation, deletion, joining, leaving, information commands, synchronization, reload, help, and error handling. You can also customize error messages and command outputs to improve clarity and UX.

***

## 💡 Breakdown of the `messages.yml` file

#### 🔖 `prefix`

```yaml
prefix: "#ffbaff&lx&r&lTeams &8»&r"
```

**prefix:** Defines the primary prefix for all messages. Supports legacy color codes `&` and hex `#RRGGBB` / `&x&R&R&G&G&B&B`. (Placeholders: —)

***

## 💻 Commands return messages

### `commands.create`

```yaml
commands:
  create:
    success: "%prefix% #a0ff72✔ You created the '%team%' team."
```

**success:** Shown when a team is successfully created. _(Placeholders: `%prefix%`, `%team%`)_

***

### `commands.delete`

```yaml
  delete:
    success: "%prefix% #a0ff72✔ You have deleted team '%team%'."
    successall: "%prefix% #a0ff72✔ You have deleted all the teams."
```

**success:** Displays when a team is deleted. _(Placeholders: `%prefix%`, `%team%`)_\
**successall:** Shows when **all** teams have been deleted. _(Placeholders: `%prefix%`)_

***

### `commands.setdisplay`

```yaml
  setdisplay:
    success: "%prefix% #a0ff72✔ Successfully changed the displayname of '%team%' to \"%display_name%\"."
```

**success:** Shown when a team display name is successfully changed. _(Placeholders: `%prefix%`, `%team%`, `%display_name%`)_

***

### `commands.join`

```yaml
  join:
    self:
      success: "%prefix% #a0ff72✔ You joined the '%team%' team."
      success_all: "%prefix% #a0ff72✔ You joined all the teams."
    other:
      success: "%prefix% #a0ff72✔ Player '%target%' joined the %team% team."
      success_all: "%prefix% #a0ff72✔ Player '%target%' joined all teams."
      success_all_all: "%prefix% #a0ff72✔ All players joined all teams."
      success_target_all: "%prefix% #a0ff72✔ Player '%target%' joined all teams."
      success_all_target: "%prefix% #a0ff72✔ All players joined the %team% team."
```

**self/success:** Message for when a player joins a specific team themselves. _(Placeholders: `%prefix%`, `%team%`)_\
**self/success\_all:** Shown when the executing player joins **all** teams. _(Placeholders: `%prefix%`)_\
**other/success:** Shown when **another** player (`%target%`) joins a team. _(Placeholders: `%prefix%`, `%target%`, `%team%`)_\
**other/success\_all:** `%target%` joins **all** teams. _(Placeholders: `%prefix%`, `%target%`)_\
**other/success\_all\_all:** **All players** join **all** teams. _(Placeholders: `%prefix%`)_\
**other/success\_target\_all:** `%target%` joins **all** teams. _(Placeholders: `%prefix%`, `%target%`)_\
**other/success\_all\_target:** **All players** join the specified `%team%`. _(Placeholders: `%prefix%`, `%team%`)_

***

### `commands.leave`

```yaml
  leave:
    self:
      success: "%prefix% #a0ff72✔ You left the '%team%' team."
      success_all: "%prefix% #a0ff72✔ You left all the teams."
      not_in_team: "%prefix% #FF0000🚫 You are not in '%team%' team."
      not_in_anyteam: "%prefix% #FF0000🚫 You are not in any team."
    other:
      target:
        success: "%prefix% #a0ff72✔ Player '%target%' left the %team% team."
        success_all: "%prefix% #a0ff72✔ Player '%target%' left all teams."
        not_in_team: "%prefix% #FF0000🚫 Player '%target%' is not on '%team%' team."
        not_in_anyteam: "%prefix% #FF0000🚫 Player '%target%' is not on any team."
      all:
        success: "%prefix% #a0ff72✔ All players left the %team% team."
        success_all: "%prefix% #a0ff72✔ All players left all teams."
        none_in_team: "%prefix% #FF0000🚫 There are no members in team '%team%'."
        none_in_anyteam: "%prefix% #FF0000🚫 There are no players in any team."
```

**self/success:** Player leaves a team. _(Placeholders: `%prefix%`, `%team%`)_\
**self/success\_all:** Player leaves all teams. _(Placeholders: `%prefix%`)_\
**self/not\_in\_team:** Player tried to leave a team they’re not in. _(Placeholders: `%prefix%`, `%team%`)_\
**self/not\_in\_anyteam:** Player isn’t in any team. _(Placeholders: `%prefix%`)_\
**other/target/...:** Same as above but for `%target%`. _(Placeholders: `%prefix%`, `%target%`, `%team%`)_\
**other/all/...:** Bulk actions across players. _(Placeholders: `%prefix%`, `%team%`)_

***

### `commands.list`

```yaml
  list:
    empty: "%prefix% #FF0000🚫 Can't find any team."
    string:
      header: "%prefix% #fff18d📰 List of teams:"
      row: "&7  - &r%display_name% &7(%team%) &8- #ccccccPriority: #fff18d%priority%"
```

**empty:** No teams found. _(Placeholders: `%prefix%`)_\
**string/header:** Header for the list. _(Placeholders: `%prefix%`)_\
**string/row:** Per-row format: display name, code, and priority. _(Placeholders: `%display_name%`, `%team%`, `%priority%`)_

***

### `commands.teaminfo`

```yaml
  teaminfo:
    string:
      header:
        - " "
        - "%prefix% #ffdcff'%team%' team information:"
        - " "
        - "&8▪ #fff18dDisplay Name: &r%display_name%"
        - "&8▪ #fff18dPriority: &r%priority%"
        - " "
      members_header: "&8▪ #fff18dMembers:"
      no_members: "&8▪ #fff18dMembers: #FF4444None"
      members_row: "&7  - &f%member%"
      footer:
        - " "
        - "#c490c4xTeams developed by drygo.dev"
        - " "
```

**string/header:** Multi-line header block. _(Placeholders: `%prefix%`, `%team%`, `%display_name%`, `%priority%`)_\
**members\_header:** Title for members list.\
**no\_members:** Shown if the team has no members.\
**members\_row:** One entry per member. _(Placeholders: `%member%`)_\
**footer:** Customizable footer (multi-line).

***

### `commands.playerinfo`

```yaml
  playerinfo:
    string:
      header:
        - " "
        - "%prefix% #ffdcff'%target%' player information:"
        - " "
      main_team: "&8▪ #fff18dMain Team: &r%display_name% &7(%team%)"
      team_list_header: "&8▪ #fff18dHis Teams:"
      team_list_row: "&7  - &r%display_name% &7(%team%) &8- #ccccccPriority: #fff18d%priority%"
      no_teams: "%prefix% #FF4444Player '%target%' is not on a team."
      footer:
        - " "
        - "#c490c4xTeams developed by drygo.dev"
        - " "
```

**string/header:** Header for the player info view. _(Placeholders: `%prefix%`, `%target%`)_\
**main\_team:** Shows highest-priority team of the player. _(Placeholders: `%display_name%`, `%team%`)_\
**team\_list\_header:** Title for the player team list.\
**team\_list\_row:** Each team with its priority. _(Placeholders: `%display_name%`, `%team%`, `%priority%`)_\
**no\_teams:** Player has no teams. _(Placeholders: `%prefix%`, `%target%`)_\
**footer:** Customizable footer.

***

### `commands.sync`

```yaml
  sync:
    success: "%prefix% #a0ff72✔ Successfully synced %count% players."
```

**success:** Sync completed with count of affected players. _(Placeholders: `%prefix%`, `%count%`)_

***

### `commands.reload`

```yaml
  reload:
    success: "%prefix% #a0ff72✔ Reloaded config and plugin messages successfully."
```

**success:** Configuration and messages reloaded. _(Placeholders: `%prefix%`)_

***

### `commands.help` (multi-line)

```yaml
  help:
    - " "
    - " "
    - "                            #ffbaff&lx&r&lTeams &8» &r&fHelp"
    - " "
    - "                    #fff18d&lᴘʟᴜɢɪɴ ᴄᴏᴍᴍᴀɴᴅꜱ"
    - "&f  /xᴛᴇᴀᴍꜱ ʜᴇʟᴘ #707070» #ccccccShows this help message"
    - "&f  /xᴛᴇᴀᴍꜱ ʀᴇʟᴏᴀᴅ #707070» #ccccccReloads the plugin configuration"
    - "&f  /xᴛᴇᴀᴍꜱ ɪɴꜰᴏ #707070- #ccccccDisplays information about the plugin."
    - " "
    - "                       #fff18d&lᴛᴇᴀᴍꜱ ᴄᴏᴍᴍᴀɴᴅꜱ"
    - "&f  /xᴛᴇᴀᴍꜱ ᴄʀᴇᴀᴛᴇ <ᴛᴇᴀᴍ> <ᴘʀɪᴏʀɪᴛʏ> #707070- #ccccccCreate a team."
    - "&f  /xᴛᴇᴀᴍꜱ ᴅᴇʟᴇᴛᴇ <ᴛᴇᴀᴍ> #707070- #ccccccDelete a team (type * on <team> for delete all the teams)."
    - "&f  /xᴛᴇᴀᴍꜱ ꜱᴇᴛᴅɪꜱᴘʟᴀʏ \"<ᴅɪꜱᴘʟᴀʏ ɴᴀᴍᴇ>\" #707070- #ccccccSets the display name of a team."
    - "&f  /xᴛᴇᴀᴍꜱ ᴊᴏɪɴ <ᴛᴇᴀᴍ> <ᴘʟᴀʏᴇʀ> #707070- #ccccccJoin a team (leave the player empty to join yourself)."
    - "&f  /xᴛᴇᴀᴍꜱ ʟᴇᴀᴠᴇ <ᴛᴇᴀᴍ> <ᴘʟᴀʏᴇʀ> #707070- #ccccccLeave a team (leave the player empty to leave yourself)."
    - " "
    - "                 #fff18d&lɪɴꜰᴏʀᴍᴀᴛɪᴏɴ ᴄᴏᴍᴍᴀɴᴅꜱ"
    - "&f  /xᴛᴇᴀᴍꜱ ʟɪꜱᴛ #707070- #ccccccDisplays the list of teams registered."
    - "&f  /xᴛᴇᴀᴍꜱ ᴛᴇᴀᴍɪɴꜰᴏ <ᴛᴇᴀᴍ> #707070- #ccccccGet information about a team."
    - "&f  /xᴛᴇᴀᴍꜱ ᴘʟᴀʏᴇʀɪɴꜰᴏ <ᴘʟᴀʏᴇʀ> #707070- #ccccccGet information about a player."
    - " "
    - " "
```

**help:** Pre-styled help page lines.

***

## 🧭 Tab completion

```yaml
tab_complete:
  create:
    team: "Team"
    priority: "Priority"
  setdisplay:
    display_name: "Display Name"
```

**tab\_complete:** Suggestions for command arguments in tab completion.

***

## ❌ Error Messages

### `error.commands` (general)

```yaml
error:
  commands:
    unknown_command: "%prefix% #FF0000🚫 Unknown command. &7Use &f/xteams help &7to see the list of commands."
    no_permission: "%prefix% #FF0000🚫 You have no permission to use this command."
    player_not_found: "%prefix% #FF0000🚫 Can't find player '%target%'."
    team_not_found: "%prefix% #FF0000🚫 Can't find team '%team%'."
    anyteam_not_found: "%prefix% #FF0000🚫 Can't find any team."
    team_already_exists: "%prefix% #FF0000🚫 Team '%team%' already exists."
    invalid_priority: "%prefix% #FF0000🚫 Priority must be a valid integer."
```

**unknown\_command:** Used when an unknown command is run. _(Placeholders: `%prefix%`)_\
**no\_permission:** Lack of permission. _(Placeholders: `%prefix%`)_\
**player\_not\_found:** Target player not found. _(Placeholders: `%prefix%`, `%target%`)_\
**team\_not\_found:** Team not found. _(Placeholders: `%prefix%`, `%team%`)_\
**anyteam\_not\_found:** No teams exist. _(Placeholders: `%prefix%`)_\
**team\_already\_exists:** Duplicate team. _(Placeholders: `%prefix%`, `%team%`)_\
**invalid\_priority:** Invalid integer for priority. _(Placeholders: `%prefix%`)_

***

### `error.commands.team_not_specified`

```yaml
    team_not_specified:
      create: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams create <team> <priority>"
      delete: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams delete <team or *>"
      setdisplay: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams setdisplay <team> [New Display Name]"
      leave: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams leave <team or *> <player or leave blank for you>"
      join: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams join <team or *> <player or leave blank for you>"
      teaminfo: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams info <team>"
```

**create/delete/setdisplay/leave/join/teaminfo:** Shown when the required `<team>` argument is missing.

***

### `error.commands.only_player`

```yaml
    only_player:
      leave: "%prefix% #FF0000🚫 When executed by console, need to specify a player. &7Use &f/xteams leave <team or *> <player>"
      join: "%prefix% #FF0000🚫 When executed by console, need to specify a player. &7Use &f/xteams join <team or *> <player>"
```

**leave/join:** Console must specify a player. _(Placeholders: `%prefix%`)_

***

### `error.commands.priority_not_specified`

```yaml
    priority_not_specified: "%prefix% #FF0000🚫 Need to specify the priority. &7Use &f/xteams create <team> <priority>"
```

**priority\_not\_specified:** Missing `<priority>` in `create`. _(Placeholders: `%prefix%`)_

***

### `error.commands.all_not_permission`

```yaml
    all_not_permission:
      delete: "%prefix% #FF0000🚫 Don't have permission to delete all teams."
      leave: "%prefix% #FF0000🚫 Don't have permission to leave all teams."
```

**delete/leave:** Sender lacks bulk-action permissions.

***

### `error.commands.leave`

```yaml
    leave:
      self:
        not_in_team: "%prefix% #FF0000🚫 You are not in '%team%' team."
        not_in_anyteam: "%prefix% #FF0000🚫 You are not in any team."
      other:
        not_in_team: "%prefix% #FF0000🚫 Player '%target%' is not on '%team%' team."
        not_in_anyteam: "%prefix% #FF0000🚫 Player '%target%' is not on any team."
        none_in_team: "%prefix% #FF0000🚫 There are no members in team '%team%'."
        none_in_anyteam: "%prefix% #FF0000🚫 There are no players in any team."
```

**self/other:** Conditions for leaving when membership requirements aren’t met.

***

### `error.commands.join`

```yaml
    join:
      self:
        already_in_team: "%prefix% #FF0000🚫 You are already in team %team%."
      other:
        none_target: "%prefix% #FF0000🚫 No players found to add."
        none_team_or_target: "%prefix% #FF0000🚫 No players or teams found."
```

**self/already\_in\_team:** Player already belongs to `%team%`. _(Placeholders: `%prefix%`, `%team%`)_\
**other/none\_target:** No players matched for adding. _(Placeholders: `%prefix%`)_\
**other/none\_team\_or\_target:** Neither players nor teams found. _(Placeholders: `%prefix%`)_

***

### `error.commands.setdisplay`

```yaml
    setdisplay:
      displayname_not_specified: "%prefix% #FF0000🚫 Need to add the display name. &7Use &f/xteams setdisplay <team> [New Display Name]"
      invalid_format: "%prefix% #FF0000🚫 The Display Name format is invalid. &7Remember to use double quotes: \"Display Name\"."
```

**displayname\_not\_specified:** Missing the new display name. _(Placeholders: `%prefix%`)_\
**invalid\_format:** Display name not wrapped in quotes. _(Placeholders: `%prefix%`)_

***

## 🧩 Placeholders (supported here)

* `%prefix%` — Plugin prefix defined in `prefix`.
* `%team%` — Team code/name.
* `%display_name%` — Team display name.
* `%target%` — Target player name (used in _other_ actions).
* `%priority%` — Team priority.
* `%member%` — Member name in `teaminfo`.
* `%count%` — Count of players affected in `sync`.

> Tip: You can also use placeholders from PlaceholderAPI (including xTeams placeholders) if installed.
