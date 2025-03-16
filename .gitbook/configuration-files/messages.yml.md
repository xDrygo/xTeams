# 📝 messages.yml

## 📄 **Introduction**

The <kbd>messages.yml</kbd> file allows you to customize all messages that the **xTeams** plugin sends to players, helping create a tailored experience for your server. This file includes messages for team creation, deletion, joining, leaving, and other related actions. You can also customize error messages and command outputs to improve the clarity and overall user experience.

## **💡 Breakdown of the&#x20;**<kbd>**messages.yml**</kbd>**&#x20;file:**

```yaml
prefix: "#ffbaff&lx&r&lTeams &8»&r"
```

* <kbd>**prefix**</kbd>: Defines the primary prefix for all messages. Supports regular color codes (&) and hex color codes of the format `#RRGGBB` and `&x&R&R&G&G&B&B`.

***

### **💻 Commands return messages.**

```yaml
commands:
  create:
    success: "%prefix% #a0ff72✔ You created the %team% team."
```

* <kbd>**success**</kbd>: This message is shown when a team is successfully created. _(Placeholders: %prefix%, %team%)_

```yaml
delete:
  success: "%prefix% #a0ff72✔ You have deleted team %team%."
  successall: "%prefix% #a0ff72✔ You have deleted all the teams."
```

* <kbd>**success**</kbd>: Displays when a team is deleted. _(Placeholders: %prefix%, %team%)_
* <kbd>**successall**</kbd>: Shows when all teapms have been deleted. _(Placeholders: %prefix%)_

```yaml
  setdisplay:
    success: "%prefix% #a0ff72✔ Successfully changed the displayname of %team% to '%display_name%'."
```

* <kbd>**success**</kbd>: This message is shown when the display name of a team successfully changed. _(Placeholders: %prefix%, %team%, %display\_name%)_

```yaml
join:
  other:
    success: "%prefix% #a0ff72✔ Player %player% joined the %team% team."
    successall: "%prefix% #a0ff72✔ Player %player% joined all teams."
  self:
    success: "%prefix% #a0ff72✔ You joined the %team% team."
    successall: "%prefix% #a0ff72✔ You joined all the teams."
```

* <kbd>**other/success**</kbd>: Message shown when another player successfully joins a team. _(Placeholders: %prefix%, %player%, %team%)_
* <kbd>**other/successall**</kbd>: Displays when another player joins all teams. _(Placeholders: %prefix%, %player%)_
* <kbd>**self/success**</kbd>: Message for when a player joins a team themselves. _(Placeholders: %prefix%, %team%)_
* <kbd>**self/successall**</kbd>: Shows when the player joins all teams. _(Placeholders: %prefix%)_

```yaml
leave:
  other:
    success: "%prefix% #a0ff72✔ Player %player% left the %team% team."
    successall: "%prefix% #a0ff72✔ Player %player% left all teams."
  self:
    success: "%prefix% #a0ff72✔ You left the %team% team."
    successall: "%prefix% #a0ff72✔ You left all the teams."
```

* <kbd>**other/success**</kbd>: Message shown when another player leaves a team. _(Placeholders: %prefix%, %player%, %team%)_
* <kbd>**other/successall**</kbd>: Displays when another player leaves all teams. _(Placeholders: %prefix%, %player%)_
* <kbd>**self/success**</kbd>: Message for when a player leaves a team themselves. _(Placeholders: %prefix%, %team%)_
* <kbd>**self/successall**</kbd>: Shows when the player leaves all teams. _(Placeholders: %prefix%)_

<pre class="language-yaml"><code class="lang-yaml">list:
<strong>  empty: "%prefix% #FF0000🚫 Can't find any team."
</strong>  string:
    header: "%prefix% #fff18d📰 List of teams:"
    row: "&#x26;7  - &#x26;r%display_name% &#x26;7(%team%) &#x26;8- #ccccccPriority: #fff18d%priority%"
</code></pre>

* <kbd>**empty**</kbd>: Message shown when no teams are found. _(Placeholders: %prefix%)_
* <kbd>**string/header**</kbd>: Header for the list of teams. _(Placeholders: %prefix%)_
* <kbd>**string/row**</kbd>: Format for each row in the team list, showing the team’s display name, ID, and priority.  _(Placeholders: %prefix%, %team%, %display\_name%, %priority%)_

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
      - "#c490c4xTeams developed by @eldrygo"
      - " "
```

* <kbd>**string/header**</kbd>: Header where you can write anything. _(Placeholders: %prefix%, %team%, %display\_name%, %priority%)_
* <kbd>**string/members\_header**</kbd>: Displays "Members" for the team’s member list.
* <kbd>**string/no\_members**</kbd>: Message shown when a team has no members.
* <kbd>**string/members\_row**</kbd>: Format for listing each member of the team. _(Placeholders: %member%)_
* <kbd>**string/footer**</kbd>: Footer where you can write anything in multiple lines.

<pre class="language-yaml"><code class="lang-yaml"><strong>playerinfo:
</strong>  string:
    header:
      - " "
      - "%prefix% #ffdcff'%player%' player information:"
      - " "
    main_team: "&#x26;8▪ #fff18dMain Team: &#x26;r%display_name% &#x26;7(%team%)"
    team_list_header: "&#x26;8▪ #fff18dHis Teams:"
    team_list_row: "&#x26;7  - &#x26;r%display_name% &#x26;7(%team%) &#x26;8- #ccccccPriority: #fff18d%priority%"
    no_teams: "#ff4444Player %player% is not on a team."
    footer:
      - " "
      - "#c490c4xTeams developed by @eldrygo"
      - " "
</code></pre>

* <kbd>**string/header**</kbd>: Header where you can write anything. _(Placeholders: %prefix%, %player%)_
* <kbd>**string/main\_team**</kbd>: Displays the main team of the player (The main team is the highest priority team of the player). _(Placeholders: %display\_name%, %team%)_
* <kbd>**string/team\_list\_header**</kbd>: Displays "His Teams: " for the player team's list.
* <kbd>**string/no\_members**</kbd>: Message shown when to player don't belong to any team. _(Placeholders: %player%)_
* <kbd>**string/team\_list\_row**</kbd>: Format for listing each team that the player belongs to. _(Placeholders: %display\_name%, %team%, %priority%)_
* <kbd>**string/footer**</kbd>: Footer where you can write anything in multiple lines.

***

### **❌ Error Messages**

```yaml
error:
  commands:
    unknown_command: "%prefix% #FF0000🚫 Unknown command. &7Use &f/xteams help &7to see the list of commands."
    no_permission: "%prefix% #FF0000🚫 You have no permission to use this command."
    player_not_found: "%prefix% #FF0000🚫 Can't find player %player%."
    team_not_found: "%prefix% #FF0000🚫 Can't find team %team%."
    team_already_exists: "%prefix% #FF0000🚫 Team %team% already exists."
    invalid_priority: "%prefix% #FF0000🚫 Priority must be a valid integer."
```

* <kbd>**unknown\_command**</kbd>: This message is shown when an unknown command is used. _(Placeholders: %prefix%)_
* <kbd>**no\_permission**</kbd>: Message for when a player does not have permission to use a command. _(Placeholders: %prefix%)_
* <kbd>**player\_not\_found**</kbd>: Message when a specified player is not found. _(Placeholders: %prefix%, %player%)_
* <kbd>**team\_not\_found**</kbd>: Message when a specified team is not found. _(Placeholders: %prefix%, %team%)_
* <kbd>**team\_already\_exists**</kbd>: Message when a team with the specified name already exists. _(Placeholders: %prefix%, %team%)_
* <kbd>**invalid\_priority**</kbd>: Message for invalid priority input. _(Placeholders: %prefix%)_

```yaml
team_not_specified:
  create: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams create <team> <priority>"
  delete: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams delete <team or *>"
      setdisplay: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams setdisplay <team> [New Display Name]"
  leave: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams leave <team or *> <player or leave blank for you>"
  join: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams join <team or *> <player or leave blank for you>"
  teaminfo: "%prefix% #FF0000🚫 Need to specify a team. &7Use &f/xteams info <team>"
```

* <kbd>**create**</kbd>: Message when no team is specified during the team creation command. _(Placeholders: %prefix%)_
* <kbd>**delete**</kbd>: Message when no team is specified during the team elimination command. _(Placeholders: %prefix%)_
* <kbd>**setdisplay**</kbd>: Message when no team is specified during the team display name changing command. _(Placeholders: %prefix%)_
* <kbd>**leave**</kbd>: Message when no team is specified during the team leaving command. _(Placeholders: %prefix%)_
* <kbd>**join**</kbd>: Message when no team is specified during the team joining command. _(Placeholders: %prefix%)_
* <kbd>**teaminfo**</kbd>: Message when no team is specified during the teaminfo  command. _(Placeholders: %prefix%)_

<pre class="language-yaml"><code class="lang-yaml">only_player:
<strong>  leave: "%prefix% #FF0000🚫 When executed by console, need to specify a player. &#x26;7Use &#x26;f/xteams leave &#x3C;team or *> &#x3C;player>"
</strong>  join: "%prefix% #FF0000🚫 When executed by console, need to specify a player. &#x26;7Use &#x26;f/xteams join &#x3C;team or *> &#x3C;player>"
</code></pre>

* <kbd>**only\_player/leave**</kbd>: Message when the <kbd>leave</kbd> command is executed by the console but a player is not specified. _(Placeholders: %prefix%)_
* <kbd>**only\_player/join**</kbd>: Message when the <kbd>join</kbd> command is executed by the console but a player is not specified. _(Placeholders: %prefix%)_

```yaml
create:
  priority_not_specified: "%prefix% #FF0000🚫 Need to specify the priority. &7Use &f/xteams create <team> <priority>"
```

* <kbd>**create/priority**</kbd>: Message when the sender don't specify the priority of the team to create. _(Placeholders: %prefix%)_

<pre class="language-yaml"><code class="lang-yaml"><strong>leave:
</strong>  self:
    not_in_team: "%prefix% #FF0000🚫 You are not in %team% team."
    not_in_anyteam: "%prefix% #FF0000🚫 You are not in any team"
  other:
    not_in_team: "%prefix% #FF0000🚫 You are not in %team% team."
    not_in_anyteam: "%prefix% #FF0000🚫 You are not in any team"
</code></pre>

* <kbd>**self/not\_in\_team**</kbd>: Message when a player is not in the specified team. _(Placeholders: %prefix%, %team%)_
* <kbd>**self/not\_in\_anyteam**</kbd>: Message when a player is not in any team. _(Placeholders: %prefix%)_
* <kbd>**other/not\_in\_team**</kbd>: Message when a player is not in the specified team. _(Placeholders: %prefix%, %team%)_
* <kbd>**other/not\_in\_anyteam**</kbd>: Message when a player is not in any team. _(Placeholders: %prefix%)_

```yaml
join:
  self:
    already_in_team: "%prefix% #FF0000🚫 You are already on %team% team."
  other:
    already_in_team: "%prefix% #FF0000🚫 Player %player% is already on %team% team."
```

* <kbd>**other/already\_in\_team**</kbd>: Message when another player is already in the specified team. _(Placeholders: %prefix%, %player%, %team%)_
* <kbd>**self/already\_in\_team**</kbd>: Message when the player trying to join is already in the specified team. _(Placeholders: %prefix%, %team%)_

```yaml
setdisplay:
  displayname_not_specified: "%prefix% #FF0000🚫 Need to add the display name. &7Use &f/xteams setdisplay <team> [New Display Name]"
  invalid_format: "%prefix% #FF0000🚫 The Display Name format is invalid. &7Remember to use double quotes: \"Display Name\"."
```

* <kbd>**displayname\_not\_specified**</kbd>: Message when a player uses the command without adding the new display name in it. _(Placeholders: %prefix%)_
* <kbd>**invalid\_format**</kbd>: Message when the Display Name is not valid. _(Placeholders: %prefix%)_

***

## 🧩 Placeholders

{% hint style="info" %}
You can also use placeholders from other expansions of [PlaceholderAPI](../xteams/setup-xteams.md#optional-dependencies) including the [xTeams placeholders](../plugin/placeholders.md).
{% endhint %}

* <kbd>**%prefix%**</kbd>: Returns the plugin prefix, defined on the prefix line of the message config.
* <kbd>**%team%**</kbd>: Returns the code name of the team involved.
* <kbd>**%player%**</kbd>: Returns the code name of the player involved.
* <kbd>**%display\_name%**</kbd>: Returns the display name of the team involved.
* <kbd>**%priority%**</kbd>: Returns the priority of the team involved.
* <kbd>**%member%**</kbd>: Returns the member name. _(This one is applicated on `/xteams teaminfo` command.)_
