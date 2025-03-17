# ⭐ xTeams

xTeams is a powerful and flexible system designed to enhance your server with advanced team management features. Whether you're hosting intense PvP matches, cooperative events, or roleplay adventures, XTeams offers the tools you need to manage your teams efficiently.

## ✅ Features
- **Administration commands** to manage the teams in-game or in the console.
- **Priority System** to control team organization.
- **Team Display Names** to customize the team's appearance.
- **Info. Commands** for players and teams.
- **Message configuration file** to customize every message of the plugin.
- **xTeams API** to use its functions in your projects.
- P**laceholderAPI Support** for enhanced customization.

## ⌨️ Commands
List of all commands from the plugin. (All the commands can be  used from the console.)

**Plugin** commands are the general commands that no manage the plugin, not a whitelist.  
**Regular Whitelist** commands manage the regular MySQL whitelist.  
**Maintenance Whitelist** commands manage the staff whitelist.

### Plugin
- **/xteams help** » Displays a list of all available commands in **xTeams**. Useful if you're unsure about the plugin’s functionalities.
- **/xteams reload** » Reloads the plugin’s configuration and settings.
- **/xteams info** » Shows detailed information about the plugin, such as the version and author.

### Team Management
- **/xteams create [team_name] [priority]** » Creates a new team with the codename and priority specified.
- **/xteams delete [team_name]** » Deletes a team and all its data permanently. On the second argument you can type the team codename or * for delete all teams.
- **/xteams setdisplay [team_name] "[display_name]"** » Sets the display name for a team, allowing it to be shown in lists and members info.
- **/xteams join [team_name] [player]** » Adds the player to the specified team. You can leave empty the player argument to join yourself the team.
- **/xteams leave [team_name] [player]** » Removes the player to the specified team. You can leave empty the player argument to leave yourself the team.

### Infomation
- **/xteams list** » Creates a new team with the codename and priority specified.
- **/xteams teaminfo [team_name]** » Creates a new team with the codename and priority specified.
- **/xteams playerinfo [player]** » Creates a new team with the codename and priority specified.

## 🔒 Permissions
### Plugin
- **xteams.command.help** » Permission to use `/xteams help`
- **xteams.command.reload** » Permission to use `/xteams reload`
- **xteams.command.info** » Permission to use `/xteams info`

### Team Management
- **xteams.command.create** » Permission to use `/xteams create`
- **xteams.command.delete** » Permission to use `/xteams delete`
- **xteams.command.delete.all** » Permission to use `/xteams delete *`
- **xteams.command.join** » Permission to use `/xteams join`
- **xteams.command.leave** » Permission to use `/xteams leave`
- **xteams.command.leave.all** » Permission to use `/xteams leave *`

### Infomation
- **xteams.command.list** » Permission to use `/xteams list`
- **xteams.command.teaminfo** » Permission to use `/xteams teaminfo`
- **xteams.command.playerinfo** » Permission to use `/xteams player`

### All permissions
- **xteams.admin** » Permission for all. (* permission)

## 🧩 Placeholders
*(These placeholders needs PlaceholderAPI to work.)*
- **%xteams_teamname_<player>%** » Displays the team name of the specified player (replace `<player>` with the player’s name).
- **%xteams_teamdisplayname_<player>%** » Displays the display name of the team that the specified player is part of (replace `<player>`).
- **%xteams_hasplayer_<player>_<team>%** » Checks if the specified player is part of the given team (returns `true` or `false`).
- **%xteams_playercount_<team>%** » Shows the number of players in the specified team.
- **%xteams_teammembers_<team>%** » Lists the members of the specified team.
- **%xteams_teams%** » Lists all teams currently registered in the plugin.
- **%xteams_teamexists_<team>%** » Checks if the specified team exists (returns `true` or `false`).
- **%xteams_teampriority_<team>%** » Displays the priority of the specified team.`

## 📋 Requirements
- For the plugin to work, you need to use **Spigot** or **Paper** *(Also you can use Paper forks like: Purpur or Pufferfish).*
- Using **Craftbukkit** is **not supported** and the plugin will not work. For best performance, **Paper** is recommended.

## 📦 Optional Dependencies
- The plugin supports PlaceholderAPI, allowing you to use placeholders to display team-related information in various parts of your server, such as scoreboards, chat, and more.

## 💻 Full Wiki
- You can find all about the plugin like, commands, permissions, placeholders, configuration files and the API in the plugin wiki: [Check it out!](https://drygo.gitbook.io/xteams)

## ❓ Support
- If you have any questions, suggestions, or problems, you can contact me on my X: [@eldrygo](https://x.com/eldrygo) or my Discord (@xdrygo).