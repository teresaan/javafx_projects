package com.github.wesleyegberto.programmingblock.component;

import com.github.wesleyegberto.programmingblock.component.util.Clipboard;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public class WhileBlock extends FluxControlBlock {

	private String headerBackgroundImage;
	private String leftBarBackgroundImage;
	private String footerBackgroundImage;
	private String textWhileImage;
	private String textDoImage;
	private String operandImage;

	private ParamBlock firstOperand;
	private RelationalOperatorBlock operator;
	private ParamBlock secondOperand;

	public WhileBlock(String headerBackground, String leftBarBackground, String footerBackground,
					  String textWhileImage, String textDoImage, String operandImage,
					  double width, double height, boolean isTemplate) {
		super(headerBackground, null, width, height, isTemplate);

		this.headerBackgroundImage = headerBackground;
		this.leftBarBackgroundImage = leftBarBackground;
		this.footerBackgroundImage = footerBackground;
		this.textWhileImage = textWhileImage;
		this.textDoImage = textDoImage;
		this.operandImage = operandImage;

		createBlock();
	}
	
	@Override
	public WhileBlock cloneBlock() {
		return new WhileBlock(headerBackgroundImage, leftBarBackgroundImage, footerBackgroundImage, textWhileImage, textDoImage,
			operandImage, getWidth(), getHeight(), false);
	}

	@Override
	public String generateCode() {
		return code;
	}

	@Override
	protected void createBlock() {
		setCursor(Cursor.DEFAULT);
		// Layout para os componentes interno
		layout = new BorderPane();
		layout.setMinWidth(getWidth());
		getChildren().add(layout);

		// Header
		Shape shapeToClip = createHeaderShape();
		background.setClip(shapeToClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(Constants.BLOCK_HEIGHT + 16d);

		StackPane headerBackgroundPane = new StackPane();
		headerBackgroundPane.setMinSize(0, 0);
		headerBackgroundPane.setAlignment(Pos.CENTER_LEFT);
		headerBackgroundPane.setMaxHeight(Constants.BLOCK_HEIGHT + 16d);
		headerBackgroundPane.getChildren().add(background);

		headerLayout = new HBox();
		headerBackgroundPane.getChildren().add(headerLayout);

		// While
		ImageView textImageView = new ImageView(new Image(getClass().getResourceAsStream(textWhileImage)));
		headerLayout.getChildren().add(textImageView);

		ImageView firstOperandImgVw = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		firstOperandImgVw.setFitHeight(Constants.BLOCK_HEIGHT);
		if(!isTemplate()) {
			firstOperandImgVw.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Operand dragged at first: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof ParamBlock) {
					firstOperand = clipboard.getValue().cloneBlock();
					updateOperand(firstOperandImgVw, evt, 1, firstOperand);
				}
				clipboard.clear();
				evt.consume();
			});
		}
		headerLayout.getChildren().add(firstOperandImgVw);

		ImageView operatorImgVw = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		operatorImgVw.setFitHeight(Constants.BLOCK_HEIGHT);
		if(!isTemplate()) {
			operatorImgVw.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Operation dragged at operator: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof RelationalOperatorBlock) {
					operator = clipboard.getValue().cloneBlock();
					operator.setDragAnchor(evt.getSceneX(), evt.getSceneY());
					operator.setOnMouseDragReleased(operatorImgVw.getOnMouseDragReleased());
					headerLayout.getChildren().set(2, operator);
				}
				clipboard.clear();
				evt.consume();
			});
		}
		headerLayout.getChildren().add(operatorImgVw);


		ImageView secondOperandImgVw = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		secondOperandImgVw.setFitHeight(Constants.BLOCK_HEIGHT);
		if(!isTemplate()) {
			secondOperandImgVw.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Operand dragged at second: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof ParamBlock) {
					secondOperand = clipboard.getValue().cloneBlock();
					updateOperand(secondOperandImgVw, evt, 3, secondOperand);
				}
				clipboard.clear();
				evt.consume();
			});
		}
		headerLayout.getChildren().add(secondOperandImgVw);

		// Then
		textImageView = new ImageView(new Image(getClass().getResourceAsStream(textDoImage)));
		headerLayout.getChildren().add(textImageView);

		layout.setTop(headerBackgroundPane);

		// Center
		boxCode = new VBox(0.0);
		boxCode.setMinSize(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
		layout.setCenter(boxCode);

		// Left
		layout.setLeft(createFooterImageViewPaneFromResource(leftBarBackgroundImage));

		// Footer
		layout.setBottom(createFooterFromResource(footerBackgroundImage));

	}

	public static WhileBlockBuilder createBuilder() {
		return new WhileBlockBuilder();
	}

	public static class WhileBlockBuilder {
		private String headerBackground;
		private String leftBarBackground;
		private String footerBackground;
		private String textWhileImage;
		private String textDoImage;
		private String operandImage;

		private double width = Constants.WHILE_BLOCK_WIDTH;
		private double height = Constants.CONTROL_FLUX_BLOCK_HEIGHT;
		private boolean isTemplate;

		public WhileBlockBuilder setHeaderBackground(String headerBackground) {
			this.headerBackground = headerBackground;
			return this;
		}

		public WhileBlockBuilder setLeftBarBackground(String leftBarBackground) {
			this.leftBarBackground = leftBarBackground;
			return this;
		}

		public WhileBlockBuilder setFooterBackground(String footerBackground) {
			this.footerBackground = footerBackground;
			return this;
		}

		public WhileBlockBuilder setTextWhileImage(String textWhileImage) {
			this.textWhileImage = textWhileImage;
			return this;
		}

		public WhileBlockBuilder setTextDoImage(String textDoImage) {
			this.textDoImage = textDoImage;
			return this;
		}

		public WhileBlockBuilder setWidth(double width) {
			this.width = width;
			return this;
		}

		public WhileBlockBuilder setHeight(double height) {
			this.height = height;
			return this;
		}

		public WhileBlockBuilder setTemplate(boolean template) {
			isTemplate = template;
			return this;
		}

		public WhileBlockBuilder setOperandImage(String operandImage) {
			this.operandImage = operandImage;
			return this;
		}

		public WhileBlock build() {
			return new WhileBlock(headerBackground, leftBarBackground, footerBackground, textWhileImage, textDoImage,
							  operandImage, width, height, isTemplate);
		}
	}
}
