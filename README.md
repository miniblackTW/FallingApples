# FallingApples
- Apples will fall from now on.

## Falling Apples:
- Apples will drop when a player is under oak leaves
- When players get hit by the falling apples, they will take damage

## BadApple Effect:
- **BadApple** is a special event
- Players who eat a Falling Apple, may have a **75% chance** to trigger the **BadApple** effect
- The **BadApple** effect lasts 10 seconds and applies the following negative status effects to the player:
    - *Mining Fatigue*
    - *Nausea*
    - *Slowness*
    - *Blindness*
    - *Weakness*

## Bypass Mechanism:
- Certain players can bypass the Falling Apples, Falling Apples won't drop on them
- **Bypassed players will still take damage if hit by the Falling Apple**

# Special Item - Falling Apple:
- The Falling Apple is a special item with display name "§6Falling Apple"
- Players who are hit by the apple will get the Falling Apple, and eating it may have a **75% chance** to trigger the BadApple effect

# Commands:
`/fallingapples bypass <player>` - Add a player to the bypass list
- Permission: fallingapples.cmd

`/fallingapples unbypass <player>` - Remove a player from the bypass list
- Permission: fallingapples.cmd
