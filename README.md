# Improved Item (Fabric)

## TECHNICAL SPECIFICATIONS

## website
Download finished mod, please go to [modrinth page](https://modrinth.com/mod/improved-item)

### ENTITY CLASSES
-- ThrowingAxeEntity --
- Inheritance: Extends ```TridentEntity```
- Key Methods:
  * ```onEntityHit()```: Applies 12.0F damage via DamageSources.thrown()
  * ```getDragInWater()```: Returns 0.95F water resistance
  * ```asItemStack()```: Returns current axeStack value

-- FarAwayFromPlayerGoal --
- AI Behavior: 
  * Checks player proximity (10-block radius)
  * Uses locateFarPos() for random flee vector

### ENCHANTMENT SYSTEM
1. THROW ENCHANTMENT
- Target Items: All axe types (WOODEN_AXE to NETHERITE_AXE)
- Effect Triggers: 
  * Right-click activates ServerNetwork packet
  * Payload contains axe material code (0-5)

2. THUNDER ENCHANTMENT
- Damage Calculation: 
  ```baseDamage = level * 4.0```
  lightningBolt.spawnAt(target)

### NETWORK PACKETS
-- MouseClickPacketPayload --
- ID: "zhengzhengyiyi:mouse_click"
- Data Flow:
  1. Client -> Server: Axe material code (int)
  2. Server spawns ThrowingAxeEntity
  3. Velocity set via ```player.getRotationVector()```


## DEVELOPER INTEGRATION

HOOK EXAMPLES:
1. Listen to Axe Throw Events:
   - Subscribe to ```ServerPlayNetworking```
   - Check for ```MouseClickPacketPayload.ID```

2. Modify Damage Values:
   - Override ```onEntityHit()``` in ThrowingAxeEntity
   - Edit base_damage in config

## FAQ TROUBLESHOOTING

<details>
<summary>dev</summary>

  Q: Axes disappear after throwing?
  - Cause: pickupType set to ALLOWED but ```tryPickup()``` returns false
  - Fix: Implement inventory insertion logic
  
  Q: Lightning doesn't strike?
  - Verify: ```world.isThundering() ```
  - Check: ```player.getMainHandStack()``` has Thunder enchant

</details>
