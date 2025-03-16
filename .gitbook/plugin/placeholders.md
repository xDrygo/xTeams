# 🧩 Placeholders

## 📄 **Introduction**

Placeholders allow you to display dynamic information about teams, players, and other aspects of your plugin in a variety of formats, such as messages or titles. These placeholders can be used in various configuration files to customize the server experience.

## 🧩 Supported Placeholders

Here is a list of the available placeholders you can use with the plugin, along with the format of the returned values:

| ⚙️ Placeholder                                     | 📄 Description                                                                                             | 🔄 Returned Format                                                                       |
| -------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| <kbd>%xteams\_teamname\_\<player>%</kbd>           | Displays the team name of the specified player (replace <kbd>\<player></kbd> with the player’s name).      | **String**: Name of the team (e.g., <kbd>red</kbd>)                                      |
| <kbd>%xteams\_teamdisplayname\_\<player>%</kbd>    | Displays the display name of the team that the specified player is part of (replace <kbd>\<player></kbd>). | **String**: Display name of the team (e.g., <kbd>\&cThe Red Team</kbd>)                  |
| <kbd>%xteams\_hasplayer\_\<player>\_\<team>%</kbd> | Checks if the specified player is part of the given team (returns "true" or "false").                      | **Boolean**: "true" or "false"                                                           |
| <kbd>%xteams\_playercount\_\<team>%</kbd>          | Shows the number of players in the specified team.                                                         | **Integer**: Number of players (e.g., <kbd>3</kbd>)                                      |
| <kbd>%xteams\_teammembers\_\<team>%</kbd>          | Lists the members of the specified team.                                                                   | **String**: Comma-separated list of players (e.g., <kbd>player1, player2, player3</kbd>) |
| <kbd>%xteams\_teams%</kbd>                         | Lists all teams currently registered in the plugin.                                                        | **String**: Comma-separated list of team names (e.g., <kbd>Red, Blue, Yellow</kbd>)      |
| <kbd>%xteams\_teamexists\_\<team>%</kbd>           | Checks if the specified team exists (returns "true" or "false").                                           | **Boolean**: "true" or "false"                                                           |
| <kbd>%xteams\_teampriority\_\<team>%</kbd>         | Displays the priority of the specified team.                                                               | **Integer**: Priority number (e.g., <kbd>1</kbd>, <kbd>5</kbd>)                          |

## ❓ How to Use These Placeholders

To use these placeholders, simply insert them in the appropriate places in supported configuration files like `messages.yml` or `config.yml`. For example, if you want to show a player's team name in a message, you can use the `%xteams_teamname_<player>%` placeholder.

## 💡 Example of Use in a Server

If you have PlaceholderAPI installed, these placeholders will automatically be replaced by the corresponding values when players see messages or titles that use them.

* `%xteams_teamname_<player>%` will replace the placeholder with the name of the team the player belongs to.
* `%xteams_teampriority_<team>%` will show the priority of the given team.

### **⚙️ Example Configuration**

```yaml
team_join_message: "Welcome to team %xteams_teamname_%player%, Display Name: %xteams_teamdisplayname_%player%"
team_info_message: "Team %xteams_teamname_<team>%, Members: %xteams_teammembers_<team>%, Player Count: %xteams_playercount_<team>%"
team_exists_message: "Does Team %xteams_teamexists_<team>% exist? %xteams_teamexists_<team>%"
```



## 🔗 Compatibility with PlaceholderAPI

If PlaceholderAPI is installed on your server, the plugin integrates directly with it. You do not need to do any extra configuration to make these placeholders work. Just add them to your messages, titles, or any other display format, and the plugin will handle the rest.
