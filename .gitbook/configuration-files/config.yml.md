# 📝 config.yml

## 📄 Introduction

The config.yml file is the main configuration file of the xTeams plugin. It allows you to define teams, manage their members, set team priorities, and configure integrations such as LuckPerms and Minecraft native teams. This file is essential for customizing how teams function and interact with other systems on your server.

## 💡 Breakdown of the config.yml file

```yaml
hooks:
  luckperms:
    enabled: false
    team_groups: # [xTeams team]: <LuckPerms group>
      blue: blue
      red: red
      yellow: yellow
  minecraft_team:
    enabled: false
    team_groups: # [xTeams team]: <Minecraft team>
      blue: blue
      red: red
      yellow: yellow
  auto_team:
    enabled: false
    op_bypass: false
    team: red

teams:
  red:
    displayName: "&e&lRed Team"
    priority: 2
    members:
      - player1
      - player2
  blue:
    displayName: "&e&lBlue Team"
    priority: 1
    members:
      - player3
      - player4
  yellow:
    displayName: "&e&lYellow Team"
    priority: 0
    members:
      - player5
      - player6
```

## 🔎 Explanation

* **hooks**: Defines integrations with external systems.
  * **luckperms**: Allows linking xTeams teams with LuckPerms groups. Example: `blue` team in xTeams can be linked to `blue` LuckPerms group.
  * **minecraft\_team**: Integrates with vanilla Minecraft scoreboard teams. Useful if you want xTeams teams to sync with in-game teams.
  * **auto\_team**: Automatically assigns players to a default team when they join.
    * **enabled**: Activates or deactivates the system.
    * **op\_bypass**: If true, server operators are not auto-assigned.
    * **team**: Defines the default team (in this case `red`).
* **teams**: Main section where you define all the teams.
  * **red, blue, yellow**: Unique IDs of each team.
  * **displayName**: The formatted name shown for the team.
  * **priority**: Defines the team’s importance. Higher values mean higher priority.
  * **members**: A list of players that belong to this team.
