package de.cyn2021.cursed.client.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;

import java.util.*;

public class CaptchaScreen extends Screen {
    private final Screen previousScreen;
    private CaptchaType captchaType;
    private final List<ItemStack> items = new ArrayList<>();
    private final Set<Integer> selected = new HashSet<>();
    private final Set<Integer> targetIndexes = new HashSet<>();
    private String visualQuestion = "";
    private String triviaQuestion = "";
    private String triviaAnswer = "";
    private EditBox inputField;

    private enum CaptchaType { VISUAL, TRIVIA }

    protected CaptchaScreen(Screen previousScreen) {
        super(Component.literal("Captcha"));
        this.previousScreen = previousScreen;
    }

    public static void openFor(net.minecraft.world.entity.player.Player player) {
        Minecraft.getInstance().setScreen(new CaptchaScreen(Minecraft.getInstance().screen));
    }

    @Override
    protected void init() {
        int boxWidth = 420;
        int boxHeight = 340;
        int boxX = (this.width - boxWidth) / 2;
        int boxY = (this.height - boxHeight) / 2;

        // Title
        this.addRenderableWidget(Button.builder(
                Component.literal("Please solve the captcha to continue!"),
                b -> {}
        ).bounds(boxX + 20, boxY + 20, boxWidth - 40, 28).build());

        captchaType = new Random().nextBoolean() ? CaptchaType.VISUAL : CaptchaType.TRIVIA;
        if (captchaType == CaptchaType.VISUAL) {
            setupVisualCaptcha(boxX, boxY, boxWidth, boxHeight);
        } else {
            setupTriviaCaptcha(boxX, boxY, boxWidth, boxHeight);
        }
        // Skip button
        this.addRenderableWidget(
                Button.builder(Component.literal("Skip"), b -> {
                            Minecraft.getInstance().setScreen(new CaptchaScreen(previousScreen));
                        })
                        .bounds(boxX + boxWidth - 140, boxY + boxHeight - 40, 120, 24)
                        .build()
        );
    }

    private void setupVisualCaptcha(int boxX, int boxY, int boxWidth, int boxHeight) {
        List<VisualCaptcha> visualCaptchas = List.of(
                new VisualCaptcha("Click all Redstone Block tiles!", Items.REDSTONE_BLOCK, List.of(Items.NETHERRACK, Items.TNT, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all TNT tiles!", Items.TNT, List.of(Items.REDSTONE_BLOCK, Items.NETHERRACK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Diamond Block tiles!", Items.DIAMOND_BLOCK, List.of(Items.EMERALD_BLOCK, Items.LAPIS_BLOCK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Emerald Block tiles!", Items.EMERALD_BLOCK, List.of(Items.DIAMOND_BLOCK, Items.LAPIS_BLOCK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Lapis Block tiles!", Items.LAPIS_BLOCK, List.of(Items.DIAMOND_BLOCK, Items.EMERALD_BLOCK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Gold Block tiles!", Items.GOLD_BLOCK, List.of(Items.IRON_BLOCK, Items.COPPER_BLOCK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Iron Block tiles!", Items.IRON_BLOCK, List.of(Items.GOLD_BLOCK, Items.COPPER_BLOCK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Copper Block tiles!", Items.COPPER_BLOCK, List.of(Items.GOLD_BLOCK, Items.IRON_BLOCK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Brick tiles!", Items.BRICKS, List.of(Items.STONE, Items.NETHERRACK, Items.TNT, Items.REDSTONE_BLOCK)),
                new VisualCaptcha("Click all Netherrack tiles!", Items.NETHERRACK, List.of(Items.STONE, Items.BRICKS, Items.TNT, Items.REDSTONE_BLOCK)),
                new VisualCaptcha("Click all Stone tiles!", Items.STONE, List.of(Items.BRICKS, Items.NETHERRACK, Items.TNT, Items.REDSTONE_BLOCK)),
                new VisualCaptcha("Click all Obsidian tiles!", Items.OBSIDIAN, List.of(Items.STONE, Items.BRICKS, Items.TNT, Items.REDSTONE_BLOCK)),
                new VisualCaptcha("Click all Nether Wart Block tiles!", Items.NETHER_WART_BLOCK, List.of(Items.NETHERRACK, Items.REDSTONE_BLOCK, Items.TNT, Items.STONE)),
                new VisualCaptcha("Click all Melon Block tiles!", Items.MELON, List.of(Items.PUMPKIN, Items.HAY_BLOCK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Pumpkin tiles!", Items.PUMPKIN, List.of(Items.MELON, Items.HAY_BLOCK, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Hay Bale tiles!", Items.HAY_BLOCK, List.of(Items.MELON, Items.PUMPKIN, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Sandstone tiles!", Items.SANDSTONE, List.of(Items.RED_SANDSTONE, Items.STONE, Items.BRICKS, Items.NETHERRACK)),
                new VisualCaptcha("Click all Red Sandstone tiles!", Items.RED_SANDSTONE, List.of(Items.SANDSTONE, Items.STONE, Items.BRICKS, Items.NETHERRACK)),
                new VisualCaptcha("Click all Prismarine tiles!", Items.PRISMARINE, List.of(Items.PRISMARINE_BRICKS, Items.DARK_PRISMARINE, Items.STONE, Items.BRICKS)),
                new VisualCaptcha("Click all Prismarine Brick tiles!", Items.PRISMARINE_BRICKS, List.of(Items.PRISMARINE, Items.DARK_PRISMARINE, Items.STONE, Items.BRICKS))
        );
        VisualCaptcha chosen = visualCaptchas.get(new Random().nextInt(visualCaptchas.size()));
        visualQuestion = chosen.question;
        int gridSize = 4;
        int tileSize = 40;
        int tileSpacing = 44;
        int total = gridSize * gridSize;
        Random rand = new Random();
        int targets = 4 + rand.nextInt(3);

        items.clear();
        selected.clear();
        targetIndexes.clear();

        for (int i = 0; i < total; i++) {
            ItemStack stack = (i < targets) ? new ItemStack(chosen.target)
                    : new ItemStack(chosen.others.get(rand.nextInt(chosen.others.size())));
            items.add(stack);
        }
        Collections.shuffle(items, rand);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).is(chosen.target)) targetIndexes.add(i);
        }

        int gridStartX = boxX + (boxWidth - gridSize * tileSpacing) / 2;
        int gridStartY = boxY + 100; // Abstand zum Titel und anderen Elementen

        this.addRenderableWidget(Button.builder(Component.literal(visualQuestion), b -> {})
                .bounds(boxX + 20, boxY + 60, boxWidth - 40, 24).build());

        for (int i = 0; i < items.size(); i++) {
            int x = gridStartX + (i % gridSize) * tileSpacing;
            int y = gridStartY + (i / gridSize) * tileSpacing;
            int idx = i;
            this.addRenderableWidget(
                    Button.builder(Component.empty(), b -> {
                        if (selected.contains(idx)) selected.remove(idx);
                        else selected.add(idx);
                    }).bounds(x, y, tileSize, tileSize).build()
            );
        }

        this.addRenderableWidget(
                Button.builder(Component.literal("Check"), b -> {
                            if (selected.equals(targetIndexes)) {
                                Minecraft.getInstance().setScreen(previousScreen);
                            }
                        })
                        .bounds(boxX + 20, boxY + boxHeight - 40, 120, 24)
                        .build()
        );
    }

    private void setupTriviaCaptcha(int boxX, int boxY, int boxWidth, int boxHeight) {
        List<Map.Entry<String, String>> questions = List.of(
                Map.entry("What is the name of the final boss in Minecraft?", "enderdragon"),
                Map.entry("Which block explodes when powered by redstone?", "tnt"),
                Map.entry("What material is used to craft a furnace?", "cobblestone"),
                Map.entry("Which animal drops leather?", "cow"),
                Map.entry("How many hearts does a player have?", "10"),
                Map.entry("Which block is NOT natural: Redstone Block, Netherrack, TNT?", "tnt"),
                Map.entry("What is the rarest biome?", "mushroom island"),
                Map.entry("What effect gives water breathing?", "water breathing"),
                Map.entry("How many eyes of ender are needed for an End Portal?", "12"),
                Map.entry("Which mob drops wither skeleton skulls?", "wither"),
                Map.entry("What material is used to craft a compass?", "iron"),
                Map.entry("Which block can produce light level 15?", "torch"),
                Map.entry("What is the boss in the Nether called?", "wither"),
                Map.entry("Which potion makes you invisible?", "invisibility"),
                Map.entry("How many blocks tall is a player?", "2"),
                Map.entry("Which block is unbreakable in survival?", "bedrock"),
                Map.entry("What is the rarest ore block?", "emerald"),
                Map.entry("Which hostile mob can fly?", "phantom"),
                Map.entry("What item repairs Elytra?", "phantom membrane"),
                Map.entry("Which block only grows in the Nether?", "nether wart")
        );
        var entry = questions.get(new Random().nextInt(questions.size()));
        triviaQuestion = entry.getKey();
        triviaAnswer = entry.getValue();

        this.addRenderableWidget(Button.builder(Component.literal(triviaQuestion), b -> {})
                .bounds(boxX + 20, boxY + 60, boxWidth - 40, 24).build());

        inputField = new EditBox(this.font, boxX + 40, boxY + 100, boxWidth - 80, 24, Component.literal("Enter answer"));
        this.addRenderableWidget(inputField);

        this.addRenderableWidget(
                Button.builder(Component.literal("Check"), b -> {
                            if (inputField.getValue().trim().equalsIgnoreCase(triviaAnswer)) {
                                Minecraft.getInstance().setScreen(previousScreen);
                            }
                        })
                        .bounds(boxX + 20, boxY + boxHeight - 40, 120, 24)
                        .build()
        );
    }

    @Override
    public void render(net.minecraft.client.gui.GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        // Soft dark background
        guiGraphics.fillGradient(0, 0, this.width, this.height, 0xA0000000, 0xC0000000);
        int boxWidth = 420;
        int boxHeight = 340;
        int boxX = (this.width - boxWidth) / 2;
        int boxY = (this.height - boxHeight) / 2;

        // Box with shadow and rounded corners
        guiGraphics.fill(boxX - 6, boxY - 6, boxX + boxWidth + 6, boxY + boxHeight + 6, 0x40000000);
        guiGraphics.fill(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0xFF222233);

        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        if (captchaType == CaptchaType.VISUAL) {
            int gridSize = 4;
            int tileSize = 40;
            int tileSpacing = 44;
            int gridStartX = boxX + (boxWidth - gridSize * tileSpacing) / 2;
            int gridStartY = boxY + 100;
            for (int i = 0; i < items.size(); i++) {
                int x = gridStartX + (i % gridSize) * tileSpacing;
                int y = gridStartY + (i / gridSize) * tileSpacing;
                guiGraphics.renderItem(items.get(i), x + 8, y + 8);
                if (selected.contains(i)) {
                    guiGraphics.fill(x, y, x + tileSize, y + tileSize, 0x80FFAA00);
                }
            }
        }
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(previousScreen);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    private static class VisualCaptcha {
        final String question;
        final Item target;
        final List<Item> others;
        VisualCaptcha(String question, Item target, List<Item> others) {
            this.question = question;
            this.target = target;
            this.others = others;
        }
    }
}