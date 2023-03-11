package com.sm.smarthome.CustomControls.Keyboard.skins;

import com.sm.smarthome.CustomControls.Keyboard.Keyboard.Key;
import com.sm.smarthome.CustomControls.Keyboard.KeyboardView;
import com.sm.smarthome.CustomControls.Keyboard.KeyboardView.Mode;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyView extends KeyViewBase<Key> {
    private static final Logger LOG = Logger.getLogger(KeyView.class.getName());

    private final VBox vBox = new VBox();
    private String text;
    private Background colorTemp;

    public KeyView(KeyboardView keyboardView, Key key) {
        super(keyboardView, key);

        vBox.setFillWidth(true);
        vBox.setAlignment(Pos.CENTER);

        getChildren().add(vBox);

        keyboardView.modeProperty().addListener(it -> updateLabels());

        visibleProperty().bind(Bindings.isNotEmpty(vBox.getChildren()));

        updateLabels();

        Platform.runLater(() -> colorTemp = getBackground());

        setOnMousePressed(mouseEvent -> {
            setStyle("-fx-background-color:" + keyboardView.getAccentColorCss() +"!important;");
            if (text == null) {
                if (showExtraKeysThread != null) {
                    showExtraKeysThread.cancel();
                }

                showExtraKeysThread = new ShowExtraKeysThread();
                showExtraKeysThread.start();
            }
        });
        setOnMouseReleased(mouseEvent -> {
            if (showExtraKeysThread != null) {
                showExtraKeysThread.cancel();
            }

            if (showingPopOver) {
                showingPopOver = false;
                return;
            }

            final boolean shiftDown = getKeyboardView().getMode().equals(Mode.SHIFT) || getKeyboardView().getMode().equals(Mode.CAPS);

            KeyEvent ke = new KeyEvent(
                    KeyEvent.KEY_TYPED,
                    text == null ? getCharacter() : text,
                    text == null ? getCharacter() : text,
                    null,
                    shiftDown,
                    false,
                    false,
                    false);

            final Node focusOwner = getKeyboardView().getScene().getFocusOwner();
            if (focusOwner != null) {
                focusOwner.fireEvent(ke);
            }

            if (text != null) {
                hidePopover();
            }

            if (getKeyboardView().getMode().equals(Mode.SHIFT)) {
                getKeyboardView().setMode(Mode.STANDARD);
            }

            var f = colorTemp.getFills().get(0).getFill();
            setStyle("-fx-background-color:" + f.toString() + "!important;");
        });
    }

    public KeyView(KeyboardView keyboardView, Key key, String text) {
        this(keyboardView, key);
        this.text = text;
        updateLabels();
    }

    private boolean showingPopOver;

    private void showPopOver() {
        List<String> list = null;
        final Key key = getKey();
        switch (getKeyboardView().getMode()) {
            case STANDARD:
                if (key.getCharacters().size() > 1) {
                    list = key.getCharacters().subList(1, key.getCharacters().size());
                }
                break;
            case SHIFT:
            case CAPS:
                if (key.getShiftCharacters().size() > 1) {
                    list = key.getShiftCharacters().subList(1, key.getShiftCharacters().size());
                }
                break;
            case SYMBOLS:
                if (key.getSymbols().size() > 1) {
                    list = key.getSymbols().subList(1, key.getSymbols().size());
                }
                break;
        }

        if (list != null) {
            KeyboardViewSkin.KeyPopoverInfo info = new KeyboardViewSkin.KeyPopoverInfo();
            info.key = getKey();
            info.text = list;
            info.width = getWidth();
            info.height = getHeight();
            info.targetKeyView = this;
            getKeyboardView().getProperties().put("keyboard.extra.keys", info);
            showingPopOver = true;
        } else {
            hidePopover();
        }
    }

    private void hidePopover() {
        getKeyboardView().getProperties().put("keyboard.extra.keys.hide", true);
    }

    private void updateLabels() {
        Key key = getKey();

        vBox.getChildren().clear();

        if (text != null) {

            vBox.getChildren().add(createLabel(text));

        } else {
            switch (getKeyboardView().getMode()) {

                case STANDARD:
                    if (!key.getShiftCharacters().isEmpty() && key.isShowShiftSymbol()) {
                        vBox.getChildren().add(createLabel(key.getShiftCharacters().get(0)));
                        if (!getStyleClass().contains("dual")) {
                            getStyleClass().add("dual");
                        }
                    }

                    if (!key.getCharacters().isEmpty()) {
                        final String text = key.getCharacters().get(0);
                        vBox.getChildren().add(createLabel(text));
                    }
                    break;
                case SHIFT:
                case CAPS:
                    if (!key.getShiftCharacters().isEmpty()) {
                        vBox.getChildren().add(createLabel(key.getShiftCharacters().get(0)));
                    }

                    getStyleClass().remove("dual");
                    break;
                case SYMBOLS:
                    if (!key.getSymbols().isEmpty()) {
                        vBox.getChildren().add(createLabel(key.getSymbols().get(0)));
                    }
                    getStyleClass().remove("dual");
                    break;
            }
        }
    }

    class ShowExtraKeysThread extends Thread {

        private boolean running = true;

        public ShowExtraKeysThread() {
            setName("Show Extra Keys Thread");
            setDaemon(true);
        }

        public void cancel() {
            running = false;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(getKeyboardView().getExtraKeysPopOverDelay());
                if (running) {
                    Platform.runLater(() -> showPopOver());
                }
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, "interrupted exception", e);
            }
        }
    }

    private ShowExtraKeysThread showExtraKeysThread;

    private String getCharacter() {
        switch (getKeyboardView().getMode()) {
            case SHIFT:
            case CAPS:
                return getKey().getShiftCharacters().get(0);
            case SYMBOLS:
                return getKey().getSymbols().get(0);
            case STANDARD:
            default:
                return getKey().getCharacters().get(0);
        }
    }
}
