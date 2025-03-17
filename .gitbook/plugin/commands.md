# ⌨️ Commands

## 📄 **Introduction**

In this section, you’ll find the list of commands available in **xTeams**. These commands allow you to manage teams, players, and settings within the plugin. Below is a table with each command, an example of how to use it, and a brief description of its functionality.

## **⌨️ Management and information commands**

| 💬 Command                                                                    | 💡 Example                                             | 📄 Description                                                                                                                 |
| ----------------------------------------------------------------------------- | ------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------ |
| **/xteams help**                                                              | <kbd>/xteams help</kbd>                                | Displays a list of all available commands in **xTeams**. Useful if you're unsure about the plugin’s functionalities.           |
| **/xteams reload**                                                            | <kbd>/xteams reload</kbd>                              | Reloads the plugin’s configuration and settings.                                                                               |
| **/xteams info**                                                              | <kbd>/xteams info</kbd>                                | Shows detailed information about the plugin, such as the version and author.                                                   |
| **/xteams create&#x20;**<kbd>**\<team\_name>**</kbd>                          | <kbd>/xteams create red</kbd>                          | Creates a new team with the codename specified.                                                                                |
| **/xteams delete&#x20;**<kbd>**\<team\_name / \* to delete all teams>**</kbd> | <kbd>/xteams delete red</kbd>                          | Deletes a team and all its data permanently. On the second argument you can type the team codename or \* for delete all teams. |
| **/xteams join&#x20;**<kbd>**\<team\_name> \<player\_name>**</kbd>            | <kbd>/xteams join red xDrygo</kbd>                     | Adds the player to the specified team. You can leave empty the player argument to join yourself the team.                      |
| **/xteams leave&#x20;**<kbd>**\<team\_name> \<player\_name>**</kbd>           | <kbd>/xteams leave red xDrygo</kbd>                    | Removes the player to the specified team. You can leave empty the player argument to leave yourself the team.                  |
| **/xteams list**                                                              | <kbd>/xteams list</kbd>                                | Displays a list of all teams registered on the server.                                                                         |
| **/xteams teaminfo&#x20;**<kbd>**\<team\_name>**</kbd>                        | <kbd>/xteams teaminfo red</kbd>                        | Displays detailed information about a specific team, such as its members and status.                                           |
| **/xteams playerinfo&#x20;**<kbd>**\<player\_name>**</kbd>                    | <kbd>/xteams playerinfo xDrygo</kbd>                   | Displays information about a player, such as which team they are currently in.                                                 |
| **/xteams setdisplay&#x20;**<kbd>**\<team\_name> \<display\_name>**</kbd>     | <kbd>/xteams setdisplay alpha "\&cThe Red Team!"</kbd> | Sets the display name for a team, allowing it to be shown in lists and members info.                                           |

***

## 📜 Explanation

* **General Use**: Most of the commands are self-explanatory and are designed to give admins and players control over the teams within your server.
* **Team Creation and Deletion**: Use the `/xteams create` to form a new team and `/xteams delete` to remove one completely. This is vital for maintaining organized and dynamic groups within your server.
* **Joining and Leaving Teams**: The `/xteams join` and `/xteams leave` commands manage player memberships, which allows you to add or remove players to/from teams quickly.
* **Team and Player Information**: For deeper management, the `/xteams teaminfo` and `/xteams playerinfo` commands provide valuable insight into team composition and player statistics.
* **Custom Display Name**: The `/xteams setdisplay` command is a great addition for personalizing your teams, as it allows you add a Display Name instead of using the codename con lists or other places.
