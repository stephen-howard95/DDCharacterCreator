package com.example.ddcharactercreator;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CharacterAdapter mCharacterAdapter;
    private MutableLiveData<List<Character>> listOfCharacters = new MutableLiveData<List<Character>>();
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SPELL_API_BASE_URL = "https://api.open5e.com/";
    private SpellDatabase mSpellDb;
    private CharacterViewModel characterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);

        AdView mAdView = findViewById(R.id.adView);
        //noinspection deprecation
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mSpellDb = SpellDatabase.getInstance(getApplicationContext());
        List<Spell> spellsList = mSpellDb.spellDao().loadAllSpells();
        if(spellsList.size() == 0){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SPELL_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
            for(int i=1; i<=7; i++){
                Call<SpellContainer> call = jsonPlaceholderApi.getSpellContainer(i);
                call.enqueue(new Callback<SpellContainer>() {
                    @Override
                    public void onResponse(Call<SpellContainer> call, Response<SpellContainer> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        } else{
                            if(response.body() != null && !response.body().getResults().isEmpty()){
                                for(int j=0; j<response.body().getResults().size(); j++){
                                    mSpellDb.spellDao().insertSpell(response.body().getResults().get(j));
                                    //There is a duplicate spell in the API. This deletes one of the copies.
                                    if(response.body().getResults().get(j).getSpellName().equalsIgnoreCase("Eye bite")){
                                        mSpellDb.spellDao().deleteSpell(response.body().getResults().get(j));
                                    }
                                    //Manually adds spells missing from the API
                                    mSpellDb.spellDao().insertSpell(new Spell("Arcane Gate", "You create 2 linked portals that remain open for the duration. Choose 2 points on the ground you can see. One within 10 feet of you, and one within 500 feet of you. A circular portal, 10 feet in diameter opens up hovering over both spots you chose. A ring is only visible from one side, which is the side that functions as a portal. In both portals, there is an opaque mist that blocks vision. On your turn, you can rotate the rings as a bonus action so that the active side is facing a different direction", "", "500 feet", "up to 10 minutes", "yes", "1 action", 6, "Sorcerer, Warlock, Wizard", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Armor of Agathys", "A protective magical force surrounds you manifesting as a spectral frost that covers you and your gear. You gain 5 temporary Hit Points for the duration. If a creature hits you with a melee attack while you have these Hit Points, the creature takes 5 cold damage.", "When you cast this spell using a higher level spell slot, both the temporary HP and cold damage increase by 5 for each spell slot above the 1st.", "Self", "1 Hour", "no", "1 Action", 1, "Warlock", "Abjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Arms of Hadar", "You invoke the power of Hadar, the Dark Hunger. Tendrils of dark energy erupt from you and batter all creatures within 10 feet of you. Each creature in that area must make a Strength saving throw. On a failed save, a target takes 2d6 Necrotic damage and can't take reactions until its next turn. On a successful save, the target takes half damage, but suffers no additional effect.", "When you cast this spell using a higher level spell slot, the damage increases by 1d6 for each spell slot level above the 1st", "Self (10-foot radius)", "Instantaneous", "no", "1 Action", 1, "Warlock", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Aura of Life", "Life-preserving energy radiates from you in an aura with a 30-foot radius. Until the spell ends, the aura moves with you, centered on you and each creature not hostile to you in the aura has resistance to Necrotic damage and their hit point maximum cannot be reduced. In addition, any nonhostile living creature that starts its turn with 0 HP and in the aura gains 1 HP.", "", "Self (30-foot radius)", "up to 10 minutes", "yes", "1 Action", 4, "Paladin", "Abjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Aura of Purity", "Purifying energy radiates from you in an aura with a 30-foot radius. Until the spell ends, the aura moves with you, centered on you and each creature not hostile to you in the aura cannot become diseased, has resistance to Poison damage, and has advantage on saving throws against effects that cause any of the following conditions: Blinded, Charmed, Deafened, Frightened, Paralyzed, Poisoned, or Stunned.", "", "Self (30-foot radius)", "up to 10 minutes", "yes", "1 Action", 4, "Paladin", "Abjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Aura of Vitality", "Healing energy radiates from you in an aura with a 30-foot radius. Until the spell ends, the aura moves with you, centered on you and you can use a bonus action to cause one creature within the aura to regain 2d6 hit points.", "", "Self (30-foot radius)", "up to 1 minute", "yes", "1 Action", 3, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Banishing Smite", "The next time you hit a creature with a weapon attack before this spell ends, your weapon crackles with force and the attack deals an extra 5d10 force damage. If this attack reduces the target to 50 or less HP, you banish it. If the target is native to a different plane of existence, it disappears, returning to its home plane. If the target is native to the plane you are on, it is sent to a harmless demiplane until the spell ends. While there, the target is incapacitated. When the spell ends, the target reappears in the closest unoccupied space to where it was before.", "", "Self", "up to 1 minute", "yes", "1 bonus action", 5, "Paladin", "Abjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Beast Sense", "You touch a willing beast. For the duration, you can use your action to see through the beasts eyes and hear what it hears and continue to do so until you use your action to return to your normal senses. While perceiving through the beasts senses, you gain the benefits of any special senses the beast has, but are blinded and deafened to your own surroundings", "", "Touch", "up to 1 hour", "yes", "1 Action", 2, "Druid, Ranger", "Divination"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Blade Ward", "You extend your hand and trace a sigil of warding in the air. Until the end of your next turn, you have resistance against Bludgeoning, Piercing, and Slashing damage dealt by weapon attacks.", "", "Self", "1 Round", "no", "1 Action", 0, "Bard, Sorcerer, Warlock, Wizard", "Abjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Blinding Smite", "The next time you hit a creature with a melee weapon attack during this spell's duration, your weapon flares with bright light and the attack deals an extra 3d8 radiant damage. The target must also succeed on a Constitution saving throw or be blinded until the spell ends. A creature blinded by this spell makes a Constitution saving throw at the end of each of it's turns. On a successful save, it is no longer blinded.", "", "Self", "up to 1 minute", "yes", "1 bonus action", 3, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Chromatic Orb", "You hurl a 4-inch diameter sphere of energy at a creature you can see within range. You choose acid, cold, fire, lightning, poison, or thunder for the type of orb you create, then make a ranged spell attack against the target. If it hits, the target takes 3d8 damage of the type you chose.", "When you cast this spell using a higher level spell slot, the damage increases by 1d8 for each spell slot above the 1st", "90 feet", "Instantaneous", "no", "1 Action", 1, "Sorcerer, Wizard", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Circle of Power", "Divine energy radiates from you, distorting and diffusing magical energy within 30 feet of you. Until the spell ends, each friendly creature within this aura has advantage on saving throws against spells and other magical effects. Additionally, When an affected creature succeeds on a saving throw against a spell or magical effect that allows it to take half damage on a success, it instead takes no damage on a success.", "", "Self (30-foot Radius)", "up to 10 minutes", "yes", "1 Action", 5, "Paladin", "Abjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Cloud of Daggers", "You fill the air with spinning daggers in a 5-foot cube centered on a point you choose within range. A creature takes 4d4 slashing damage when it enters the spells area for the first time or starts its turn there.", "When you cast this spell using a higher level spell slot, the damage increases by 2d4 for each slot level above 2nd.", "60 feet", "up to 1 minute", "yes", "1 Action", 2, "Bard, Sorcerer, Warlock, Wizard", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Compelled Duel", "You attempt to compel a creature into a duel. One creature you can see within range must make a Wisdom saving throw. On a failed save, the creature is drawn to you, compelled by your divine demand. For the duration, it has disadvantage on attack rolls against any creature besides you and must make a Wisdom saving throw every time it tries to move to a spot more than 30 feet from you. If it succeeds this saving throw, the creature's movement is not restricted this turn.\nThe spell ends if you attack any other creature, if you cast a spell that targets a hostile creature other than the target, if a creature friendly to you damages tha target/casts a harmful spell on it, or if you end your turn more than 30 feet away form the target", "", "30 feet", "up to 1 minute", "yes", "1 bonus action", 1, "Paladin", "Enchantment"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Conjure Barrage", "You throw a nonmagical weapon or fire a piece of nonmagical ammunition into the air to create a cone of identical weapons that shoot forward and disappear. Each creature in a 60-foot cone must succeed on a DEX saving throw or take 3d8 damage , half as much on a successful save. The damage type is the same as that of the weapon or ammunition used.", "", "Self (60-foot Cone)", "Instantaneous", "no", "1 Action", 3, "Ranger", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Conjure Volley", "You fire a piece of nonmagical ammunition or throw a nonmagical weapon in the air and choose a point within range. Hundreds of duplicates of the weapon or ammunition used/thrown fall in a volley from above then disappear. Each creature in a 40-foot radius, 20-foot high cylinder centered on that point must make a DEX saving throw or take 8d8 damage, half as much on a successful save. The damage type is the same as the ammunition/weapon used.", "", "150 feet", "Instantaneous", "no", "1 Action", 5, "Ranger", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Cordon of Arrows", "You plant 4 pieces of nonmagical ammunition (arrows or crossbow bolts) in the ground within range and lay magic upon them to protect an area. Until the spell ends, whenever a creature other than you comes within 30 feet of the ammunition or starts their turn there, one piece of ammunition flies up to strike it. The creature must make a DEX saving throw or take 1d6 Piercing damage. The ammunition is then destroyed. The spell ends when no more ammunition remains. When you cast this spell you can designate any creatures you choose for the spell to ignore.", "When you cast this spell using a higher level spell slot, the amount of ammunition that can be affected increases by 2 for each spell slot above 2nd level.", "5 feet", "8 hours", "no", "1 Action", 2, "Ranger", "Transmutation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Crown of Madness", "One humanoid of your choice that you can see within range must succeed on a WIS saving throw or become charmed by you for the duration. While the target is charmed in this way,  a twisted crown of jagged iron appears on its head and a madness glows in their eyes. The charmed target must use its action before moving on each of tis turns to make a melee attack against a creature other than itself that you mentally choose. The target may act normally on its turn if you choose no creature or if no one is in their melee range. On your subsequent turns, you must use your action to maintain control over the target or the spell ends. The target can also make a WIS saving throw at the end of each of its turns, ending the spell on a success", "", "120 feet", "up to 1 minute", "yes", "1 Action", 2, "Bard, Sorcerer, Warlock, Wizard", "Enchantment"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Crusader's Mantle", "Holy power radiates from you in an aura within a 30-foot radius, awakening boldness in friendly creatures. The aura moves with you, cantered on you. While in the aura, each non-hostile creature in the aura, including yourself, deal an extra 1d4 Radiant damage when it hits with a weapon attack", "", "Self", "up to 1 minute", "yes", "1 Action", 3, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Destructive Wave", "You strike the ground, creating a burst of divine energy that ripples outward from you. Each creature you choose within 30 feet of you must succeed on a Constitution saving throw or take 5d6 Thunder damage, as well as 5d6 Radiant or Necrotic damage (your choice), and be knocked prone. A creature that succeeds on its saving throw takes half as much damage and is not knocked prone.", "", "Self(30-foot radius)", "Instantaneous", "no", "1 Action", 5, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Dissonant Whispers", "You whisper a Discordant melody that only one creature of your choice within range can hear, wracking it with terrible pain. The target must make a Wisdom saving throw. On a failed save, it takes 3d6 psychic damage and must immediately use its reaction, if available, to move as far as its speed allows away from you. The creature doesn't move into obviously dangerous ground, such as fire or a pit. On a successful save, the target takes half as much damage and doesn't have to move. A deafened creature automatically succeeds on the save.", "When you cast this spell at a higher level, the damage increases by 1d6 for each spell slot above the 1st.", "60 feet", "Instantaneous", "no", "1 Action", 1, "Bard", "Enchantment"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Elemental Weapon", "A nonmagical weapon you touch becomes magical for the duration. Choose 1 of the following damage types: Acid, Cold, Fire, Lightning, or Thunder. For the duration, the weapon has a +1 bonus to attack rolls and deals an extra 1d4 damage of the chosen type.", "When you cast this spell using a 5th or 6th level spell slot, the bonus to attack rolls becomes +2 and the extra damage is 2d4. Using a spell slot of 7th level or higher, it becomes +3 to hit and deals an extra 3d4 damage.", "Touch", "up to 1 hour", "yes", "1 Action", 3, "Paladin", "Transmutation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Ensnaring Strike", "The next time you hit a creature with a weapon attack before this spell ends, a writhing mass of thorny vines appears at the point of impact and the target must succeed at a STR saving throw or be restrained by the magical vines until the spell ends. A Large or larger creature has advantage on the saving throw. If the creature succeeds, the vines wither away. While restrained by this spell, the target takes 1d6 piercing damage at the start of each of its turns. A creature restrained by the vines or another creature that can touch the restraned creature can use its action to make a STR check against your spell save DC in order to free them. On a success, the target is freed.", "If you cast this spell using a higher level spell slot, the damage is increased by 1d6 for each spell slot above the first", "Self", "up to 1 minute", "yes", "1 Bonus Action", 1, "Ranger", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Feign Death", "You touch a willing creature and put it in a state that is indistinguishable from death.  For the duration, or until you use an action to touch the creature and dismiss the spell, the target appears dead to all outward inspection and to spells used to determine the target's status. The target is blinded, incapacitated, and it's speed drops to 0. They have resistance to all damage except psychic. If the target is diseased or poisoned when you cast the spell, the disease/poison have no effect until the spell ends.", "", "Touch", "1 hour", "no", "1 Action", 3, "Bard, Cleric, Druid, Wizard", "Necromancy"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Friends", "For the duration, you have advantage on all Charisma checks directed at 1 creature of your choice that isn't hostile toward you. When the spell ends, the creature realizes you used magic to influence its mood and may become hostile.", "", "Self", "up to 1 minute", "yes", "1 action", 0, "Bard, Sorcerer, Wizard", "Enchantment"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Grasping Vine", "You conjure a vine that sprouts from the ground in an area you can see within range. The vine lashes out at a creature of your choice within 30 feet of it that you can see. That creature must succeed on a Dexterity saving throw or be pulled 20 feet toward the vine. Until the spell ends, you may use a bonus action to have the vine lash out at the same creature or a different one.", "", "30 feet", "up to 1 minute", "yes", "1 bonus action", 4, "Druid, Ranger", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Hail of Thorns", "The next time you hit a creature with a ranged weapon attack before the spell ends, the spell creates a rain of thorns that sprouts from your ranged weapon or ammunition. In addition to the normal effect of the attack, the target and each creature within 5 feet of the target must make a DEX saving throw or take 1d10 Piercing damage, half as much damage on a successful save.", "If you cast this spell using a higher level spell slot, the damage increases by 1d10 for each spell slot above the 1st, to a maximum of 6d10.", "Self", "up to 1 minute", "yes", "1 Bonus Action", 1, "Ranger", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Hex", "You place a curse on a creature that you can see within range. Until the spell ends, you deal an extra 1d6 Necrotic damage to the target whenever you hit them with an attack. Additionally, you choose 1 ability (STR, DEX, CON, INT, WIS, or CHA) when you cast the spell, and the target has disadvantage on ability checks made with that ability. If the target drops to 0HP before the spell ends, you can use a bonus action on your turn to move the hex to another creature. A Remove Curse spell cast on the target ends the spell early.", "When you cast this spell using a spell slot of 3rd or 4th, you can maintain concentration on this spell for up to 8 hours. Using a spell slot of 5th level or higher, you can maintain concentration on this spell for up to 24 hours.", "90 feet", "up to 1 hour", "yes", "1 Bonus Action", 1, "Warlock", "Enchantment"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Hunger of Hadar", "You open a gateway to the dark between the starts, a region infested with unknown horrors. A 20-foot radius sphere of blackness and bitter cold appears, centered on a point within range and lasting for the duration. This void is filled with a cacophany of soft whispers and slurping noises that can be heard up to 30 feet away. No light, magical or otherwise, can illuminate the area and creatures fully within the area are blinded. The void creates a warp in the fabric of space and the area is difficult terrain. Any creature that starts its turn in the area takes 2d6 Cold damage. Any creature that ends its turn in the area must succeed on a DEX saving throw or take 2d6 Acid damage as milky, otherworldly tentacles rub against it.", "", "150 feet", "up to 1 minute", "yes", "1 Action", 3, "Warlock", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Lightning Arrow", "The next time you make a ranged weapon attack during the spell's duration, the weapons ammunition, or the weapon itself if it's a thrown weapon, transforms into a bolt of lightning. Make the attack roll as normal. The target takes 4d8 Lightning damage on a hit or half as much on a miss instead of the weapon's normal damage. Whether you hit or miss, each creature within 10 feet of the target must make a DEX saving throw or take 2d8 Lightning damage, half as much on a successful save. The weapon or piece of ammunition then turns back into its normal form.", "When you cast this spell using a higher level spell slot, the damage for both of the spell effects increases by 1d8 for each slot level above 3rd.", "Self", "up to 1 minute", "yes", "1 Bonus Action", 3, "Ranger", "Transmutation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Phantasmal Killer", "You tap into the nightmares of a creature you can see within range and create an illusory manifestation of it's deepest fears, visible only to that creature. The target must make a WIS saving throw or become frightened for the duration. At the end of each of the target's turns, it must make a WIS saving throw or take 4d10 psychic damage. On a successful save, the spell ends.", "When you cast this spell using a higher level spell slot, the damage increases by 1d10 for each slot level above 4th.", "120 feet", "up to 1 minute", "yes", "1 Action", 4, "Wizard", "Illusion"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Power Word Heal", "A wave of healing energy washes over the creature you touch. The target regains all its Hit Points. If the creature is charmed, frightened, paralyzed, or stunned, they no longer are. If the creature is prone, it can use its reaction to stand up. This spell has no effect on undead or constructs.", "", "Touch", "Instantaneous", "no", "1 Action", 9, "Bard", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Searing Smite", "The next time you hit with a melee weapon attack during this spell's duration, your weapon flares with a white-hot intensity, and the attack deals an extra 1d6 Fire damage and causes the target to ignite in flames. At the start of each of its turns until the spell ends, the target must make a Constitution saving throw. On a failed save, the target takes 1d6 fire damage. The spell ends if the target or a creature within 5 feet of the target use their action to douse the flames, the target succeeds on their Constitution saving throw, or if an effect douses the flames such as the target being submerged in water", "When you cast this spell at a higher level, the initial extra damage dealt by the attack increases by 1d6 for each spell slot above the first", "Self", "up to 1 minute", "yes", "1 bonus action", 1, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Staggering Smite", "The next time you hit with a melee weapon attack during this spell's duration, your weapon pierces both body and mind, and the attack deals an extra 4d6 Psychic damage. The target must then make a Wisdom saving throw. On a failed save, it has disadvantage on attack rolls and ability checks and cannot take reactions until the end of it's next turn", "", "Self", "up to 1 minute", "yes", "1 bonus action", 4, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Swift Quiver", "You transmute your quiver so it produces an endless supply of nonmagical ammunition, which seems to leap into your hand whenever you reach for it. On each of your turns until this spell ends, you can use a bonus action to make 2 attacks with a weapon that uses ammunition from the quiver. Each time you make such a ranged attack, your quiver magically replaces the ammunition with a similar piece of nonmagical ammunition. When the spell ends, any pieces of ammunition created by this spell disintegrates. If the quiver leaves your possession, the spell ends.", "", "Touch", "up to 1 minute", "yes", "1 Bonus Action", 5, "Ranger", "Transmutation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Telepathy", "You create a telepathic link between you and  a willing creature with which you are familiar. The creature can be anywhere on the same plane of existence as you, and the spell will end if you are no longer on the same plane. Until the spell ends, you and the target can instantaneously share words, images, sounds, and other sensory messages with one another through the link and the target recognizes you as the creature it is communicating with. The spell enables a creature with at least 1 Intelligence to understand the meaning of your words and take in the scope of any sensory messages you send it.", "", "Unlimited", "24 Hours", "no", "1 Action", 8, "Wizard", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Thorn Whip", "You create a long, vine-like whip covered in thorns that lashes out at your command toward a creature within range. Make a melee spell attack against the target. If it hits, the creature takes 1d6 piercing damage, and if the creature is Large or smaller, you pull the creature up to 10 feet closer to you.", "This spell deals 2d6 damage at level 5, 3d6 at level 11, and 4d6 at level 17.", "30 feet", "Instantaneous", "no", "1 Action", 0, "Druid", "Transmutation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Thunderous Smite", "The next time you hit with a melee weapon attack during this spell's duration, your weapon rings with thunder that is audible within 300 feet of you, and the attack deals an extra 2d6 Thunder damage. Additionally, if the target is a creature, it must succeed on a Strength saving throw or be pushed 10 feet away from you and knocked prone", "", "Self", "up to 1 minute", "yes", "1 bonus action", 1, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Tsunami", "A wall of water springs into existence at a point you choose within range. You can make the wall up to 300 feet long, 300 feet high, and 50 feet thick, and it lasts for the duration. When the wall appears, each creature within it's area must make a STR saving throw or take 6d10 Bludgeoning damage, or half as much on a successful save. At the start of each of your turns after the wall appears, the wall, along with any creatures within it, moves 50 feet away from you. Any Huge or smaller creatures inside the wall or whose space the wall enters when it moves must succeed at a STR saving throw or take 5d10 Bludgeoning damage. A creature can only take this damage once per round. At the end of the turn, the walls height is reduced by 50 feet and the damage creatures take from the spell on subsequent turns is reduced by 1d10. When the wall reaches 0 feet in height, the spell ends. A creature caught in the wall can move by swimming, but must make a successful STR (Athletics) check against your Spell Save DC to move at all. A creature that moves out of the area falls to the ground.", "", "Sight", "up to 6 Rounds", "yes", "1 Minute", 8, "Druid", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Witch Bolt", "A beam of crackling, blue energy lances out toward a creature you can see within range, forming a sustained arc of lightning between you and the creature. Make a ranged spell attack against the creature. On a hit, the target takes 1d12 Lightning damage and on each of your turns for the duration, you can use your action to deal 1d12 Lightning damage to the target automatically. The spell ends if you use your action to do anything else, or if the target has full cover from you or is outside the range.", "When you cast this spell at a higher level, the initial damage increases by 1d12 for each spell level above 1st.", "30 feet", "up to 1 minute", "yes", "1 Action", 1, "Sorcerer, Warlock, Wizard", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Wrathful Smite", "The next time you hit with a melee weapon attack during this spell's duration, your attack deals an extra 1d6 Psychic damage. Additionally, if the target is a creature, it mus make a Wisdom saving throw or be frightened of you until the spell ends. As an action, the creature can make a Wisdom check against your spell save DC to steel its resolve and end the spell", "", "Self", "up to 1 minute", "yes", "1 bonus action", 1, "Paladin", "Evocation"));
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<SpellContainer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        RecyclerView characterListView = findViewById(R.id.character_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        characterListView.addItemDecoration(dividerItemDecoration);
        characterListView.setLayoutManager(layoutManager);
        mCharacterAdapter = new CharacterAdapter(this, listOfCharacters.getValue());
        characterListView.setAdapter(mCharacterAdapter);
        characterViewModel.loadAllCharacters().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                mCharacterAdapter.setCharacters(characters);
            }
        });

        FloatingActionButton fab = findViewById(R.id.add_character);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewCharacter();
            }
        });
    }

    private void createNewCharacter(){
        Intent intent = new Intent(this, FirstQuestionnaireActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
