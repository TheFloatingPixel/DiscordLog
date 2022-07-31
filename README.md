# DiscordLog
Spigot plugin allowing for logging various in-game events to a discord channel.

**TODO:**
- [ ] Change template system to allow for different order and missing arguments

## Setup

1. Download plugin and add it into the plugins folder

2. Run server to create the plugin config file

3. Open discord and go to the channel that you want the events to be logged to

4. Open the channel settings

   ![obraz](https://user-images.githubusercontent.com/110129491/182033222-b875069d-c674-4cc3-833f-aa4c4b0b7859.png)

5. Go to 'Integrations'

   ![obraz](https://user-images.githubusercontent.com/110129491/182033258-928753d6-382e-4a4b-a6d2-cbde72945dca.png)

6. Click the 'Create Webhook' button and name your webhook whatever you want

   ![obraz](https://user-images.githubusercontent.com/110129491/182033277-7b730a3f-0ba0-4799-9a50-f3244508b1d4.png)

7. Press the 'Copy Webhook Url' button and paste the url under the 'webhooks' section in the config file

   ![obraz](https://user-images.githubusercontent.com/110129491/182033318-e6f22374-f240-4bca-91df-22463313f601.png)

   Your config file should now look something like this
   
   ```yml
   webhooks:
     - https://discord.com/api/webhooks/id/token
   ```
   
Aaand the setup is done! You can now further customize DiscordLog by looking into the next section.

## Config File

This section explains all the options available in the DiscordLog config file.


### **`webhooks`** 
A list of all the discord webhooks to log the events to. DiscordLog supports multiple webhooks in a single server.

**Example:**
```yml
  webhooks:
    - https://discord.com/api/webhooks/id/token
    - https://discord.com/api/webhooks/id2/token2
```

### **`logged-events`** 
A list of all the events to log. Accepted list elements are 
 - `player-command-execution`
 - `console-command-execution`
 - `player-join`
 - `player-disconnect`
 - `player-kick`
 - `player-death`
 - `player-respawn`
 - `player-kill-entity`
 - `player-kill-named-entity`

**Example:**
```yml
  logged-events:
  - player-command-execution
  - console-command-execution
  - player-join
  - player-disconnect
# - player-kick  This line is commented out, so the plugin will not log players being kicked from the server
  - player-death
  - player-respawn
  - player-kill-entity
  - player-kill-named-entity
```


### **`messages`** 
A section containing all the log message formats (they can contain discord markdown!). The values in this section are
 - `player-command` - connected to `player-command-execution` event. First `%s` represents player, second - the command that was sent.
    
 - `console-command` - connected to `console-command-execution` event. `%s` represents the command that was sent.

 - `player-join` - connected to `player-join` event. `%s` represents the player.
 
 - `player-disconnect` - connected to `player-disconnect` event. `%s` represents the player.
 
 - `player-kick` - connected to `player-kick` event. `%s` represents the player.

 - `player-death` - connected to `player-death` event. `%s` represents the player.

 - `player-respawn` - connected to `player-respawn` event. `%s` represents the player.

 - `player-kill-entity` - connected to `player-kill-entity` event. First `%s` represents the player, second - the entity type.
 
 - `player-kill-named-entity` - connected to `player-kill-named-entity` event. First `%s` represents the player, second - the entity type, and third - the entity name

**Example:**
```yml
  messages:
     player-command: Player **%s** executed the command `%s`
     console-command: CONSOLE executed the command `%s`
     player-join: Player **%s** joined the server!
     player-disconnect: Player **%s** disconnected from the server.
     player-kick: Player **%s** was kicked from the server!
     player-death: Player **%s** died!
     player-respawn: Player **%s** respawned.
     player-kill-entity: Player **%s** killed entity of type **%s**
     player-kill-named-entity: Player **%s** killed entity of type **%s**, named **%s**
```

## Commands

### `discordlog`
Syntax: `discordlog ( info | reload-config | test-webhooks )`

Subcommands:
 - `info`
 
   Displays information about the plugin
   
 - `reload-config`

   Reloads the plugin config file
   
 - `test-webhooks`
 
   Sends a test message to all webhooks

## Permissions

 - `discordlog.reloadconfig` - grants access to `/discordlog reload-config`
 
 - `discordlog.testwebhooks` - grants access to `/discordlog test-webhooks`
