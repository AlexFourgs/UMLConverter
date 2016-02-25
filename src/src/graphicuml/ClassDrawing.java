package graphicuml;

import graphicuml.ObjectPositions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.swing.JPanel;

import textualuml.LinkEnum;
import textualuml.ObjectAbstractClass;
import textualuml.ObjectClass;
import textualuml.ObjectInterface;
import textualuml.ObjectPackage;
import textualuml.ObjectProgram;
import textualuml.Link;

public class ClassDrawing extends JPanel {
	private ObjectProgram program;

	public ClassDrawing(ObjectProgram program) {
		super();
		this.setBackground(Color.WHITE);
		this.program = program;
		this.setMinimumSize(new Dimension(250, 250));
		this.setPreferredSize(new Dimension(2500, 1000));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int posInitX = 50;
		int posInitY = 50;
		int xInit = 0;
		int xFinal = 0;
		int yFinal = 0;
		int yTitle = 16;
		int yAttribut = 22;
		g.setColor(Color.red);
		for (int i = 0; i < program.getAllPackages().size(); i++) {
			ObjectPackage actualPackage = program.getAllPackages().get(i);
			for (int j = 0; j < actualPackage.getAllClass().size(); j++) {
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				if (actualClass.getLinks().size() != 0) {
					List<Link> links = actualClass.getLinks();
					for (int k = 0; k < links.size(); k++) {
						if (links.get(k).getClassLinked() != null
								|| links.get(k).getInterfaceLinked() != null) {
							if (links.get(k).getLink() == LinkEnum.IMPLEMENTS) {
								ObjectInterface classLinked = links.get(k)
										.getInterfaceLinked();
								int decal = 12;
								int decal2 = 0;
								int xActual = actualClass.getObjectPositions()
										.getX()
										+ actualClass.getObjectPositions()
												.getWidth() / 4;
								int xLinked = classLinked.getObjectPositions()
										.getX()
										+ classLinked.getObjectPositions()
												.getWidth() * 3 / 4;
								int coefYActual, coefYLinked, yActual, yLinked;
								// Quand actualClass est en dessous
								if (actualClass.getObjectPositions().getY() > classLinked
										.getObjectPositions().getY()) {
									yActual = actualClass.getObjectPositions()
											.getY();
									yLinked = classLinked.getObjectPositions()
											.getY()
											+ classLinked.getObjectPositions()
													.getHeight() + decal;
									coefYActual = 1;
									coefYLinked = 1;
									// Quand actualClass est au dessus
								} else if (actualClass.getObjectPositions()
										.getY() < classLinked
										.getObjectPositions().getY()) {
									yActual = actualClass.getObjectPositions()
											.getY()
											+ actualClass.getObjectPositions()
													.getHeight();
									yLinked = classLinked.getObjectPositions()
											.getY();
									coefYActual = -1;
									coefYLinked = -1;
									decal2 = 20;
								} else if ((actualClass.getObjectPositions()
										.getY() == classLinked
										.getObjectPositions().getY())
										&& (actualClass.getObjectPositions()
												.getX() == classLinked
												.getObjectPositions().getX())) {
									yActual = actualClass.getObjectPositions()
											.getY();
									yLinked = classLinked.getObjectPositions()
											.getY() + decal;
									coefYActual = 0;
									coefYLinked = 0;
								} else {
									yActual = actualClass.getObjectPositions()
											.getY()
											+ actualClass.getObjectPositions()
													.getHeight();
									yLinked = classLinked.getObjectPositions()
											.getY()
											+ classLinked.getObjectPositions()
													.getHeight() + decal;

									coefYActual = -1;
									coefYLinked = 1;
								}
								g.drawLine(xActual, yActual, xActual, yActual
										- 30 * coefYActual);
								g.drawLine(xActual, yActual - 30 * coefYActual,
										xLinked, yLinked + 20 * coefYLinked);
								if (links.get(k).toString()
										.contains("implements")) {
									g.drawOval(xLinked - 10, yLinked - decal2,
											20, 20);
								}
							} else {
								ObjectClass classLinked = links.get(k)
										.getClassLinked();
								int decal = 0;
								if (classLinked instanceof ObjectAbstractClass) {
									decal = 12;
								}
								int xActual = actualClass.getObjectPositions()
										.getX()
										+ actualClass.getObjectPositions()
												.getWidth() / 4;
								int xLinked = classLinked.getObjectPositions()
										.getX()
										+ classLinked.getObjectPositions()
												.getWidth() * 3 / 4;
								int coefYActual, coefYLinked, yActual, yLinked;
								if (actualClass.getObjectPositions().getY() > classLinked
										.getObjectPositions().getY()) {
									yActual = actualClass.getObjectPositions()
											.getY();
									yLinked = classLinked.getObjectPositions()
											.getY()
											+ classLinked.getObjectPositions()
													.getHeight();
									coefYActual = 1;
									coefYLinked = 1;
								} else if (actualClass.getObjectPositions()
										.getY() < classLinked
										.getObjectPositions().getY()) {
									yActual = actualClass.getObjectPositions()
											.getY()
											+ actualClass.getObjectPositions()
													.getHeight();
									yLinked = classLinked.getObjectPositions()
											.getY() + decal;
									coefYActual = -1;
									coefYLinked = -1;
								} else if ((actualClass.getObjectPositions()
										.getY() == classLinked
										.getObjectPositions().getY())
										&& (actualClass.getObjectPositions()
												.getX() == classLinked
												.getObjectPositions().getX())) {
									yActual = actualClass.getObjectPositions()
											.getY();
									yLinked = classLinked.getObjectPositions()
											.getY() + decal;
									coefYActual = 0;
									coefYLinked = 0;
								} else {
									yActual = actualClass.getObjectPositions()
											.getY()
											+ actualClass.getObjectPositions()
													.getHeight();
									yLinked = classLinked.getObjectPositions()
											.getY()
											+ classLinked.getObjectPositions()
													.getHeight() + decal;

									coefYActual = -1;
									coefYLinked = 1;
								}

								g.drawLine(xActual, yActual, xActual, yActual
										- 30 * coefYActual);
								g.drawLine(xActual, yActual - 30 * coefYActual,
										xLinked, yLinked + 20 * coefYLinked);
								if (links.get(k).toString().contains("extends")) {
									g.drawLine(xLinked - 10, yLinked + 20
											* coefYLinked, xLinked + 10,
											yLinked + 20 * coefYLinked);
									g.drawLine(xLinked - 10, yLinked + 20
											* coefYLinked, xLinked, yLinked + 1
											* coefYLinked);
									g.drawLine(xLinked + 10, yLinked + 20
											* coefYLinked, xLinked, yLinked + 1
											* coefYLinked);
								}
								if (links.get(k).toString()
										.contains("composition")) {
									g.drawLine(xLinked, yLinked + 20
											* coefYLinked, xLinked - 6, yLinked
											+ 10 * coefYLinked);
									g.drawLine(xLinked, yLinked + 20
											* coefYLinked, xLinked + 6, yLinked
											+ 10 * coefYLinked);
									g.drawLine(xLinked - 6, yLinked + 10
											* coefYLinked, xLinked, yLinked);
									g.drawLine(xLinked + 6, yLinked + 10
											* coefYLinked, xLinked, yLinked);
								}
								if (links.get(k).toString()
										.contains("aggregation")) {
									for (int l = 6; l >= 0; l--) {
										g.drawLine(xLinked, yLinked + 20
												* coefYLinked, xLinked - l,
												yLinked + 10 * coefYLinked);
										g.drawLine(xLinked, yLinked + 20
												* coefYLinked, xLinked + l,
												yLinked + 10 * coefYLinked);
										g.drawLine(xLinked - l, yLinked + 10
												* coefYLinked, xLinked, yLinked);
										g.drawLine(xLinked + l, yLinked + 10
												* coefYLinked, xLinked, yLinked);
									}
								}
							}
						}
					}
				}
			}
			for (int j = 0; j < actualPackage.getAllInterface().size(); j++) {
				ObjectInterface actualClass = actualPackage.getAllInterface()
						.get(j);
				if (actualClass.getLinks().size() != 0) {
					List<Link> links = actualClass.getLinks();
					for (int k = 0; k < links.size(); k++) {
						if (links.get(k).getClassLinked() != null
								|| links.get(k).getInterfaceLinked() != null) {
							if (links.get(k).getLink() == LinkEnum.EXTENDS) {
								ObjectInterface classLinked = links.get(k)
										.getInterfaceLinked();
								int decal = 12;
								int xActual = actualClass.getObjectPositions()
										.getX()
										+ actualClass.getObjectPositions()
												.getWidth() / 4;
								int xLinked = classLinked.getObjectPositions()
										.getX()
										+ classLinked.getObjectPositions()
												.getWidth() * 3 / 4;
								int coefYActual, coefYLinked, yActual, yLinked;
								// Si actual en dessous de linked
								if (actualClass.getObjectPositions().getY() > classLinked
										.getObjectPositions().getY()) {
									yActual = actualClass.getObjectPositions()
											.getY();
									yLinked = classLinked.getObjectPositions()
											.getY()
											+ classLinked.getObjectPositions()
													.getHeight() + decal;
									coefYActual = 1;
									coefYLinked = 1;
									// Si actual au dessus de linked
								} else if (actualClass.getObjectPositions()
										.getY() < classLinked
										.getObjectPositions().getY()) {
									yActual = actualClass.getObjectPositions()
											.getY()
											+ actualClass.getObjectPositions()
													.getHeight() + decal;
									yLinked = classLinked.getObjectPositions()
											.getY();
									coefYActual = -1;
									coefYLinked = -1;
								} else if ((actualClass.getObjectPositions()
										.getY() == classLinked
										.getObjectPositions().getY())
										&& (actualClass.getObjectPositions()
												.getX() == classLinked
												.getObjectPositions().getX())) {
									yActual = actualClass.getObjectPositions()
											.getY();
									yLinked = classLinked.getObjectPositions()
											.getY() + decal;
									coefYActual = 0;
									coefYLinked = 0;
								} else {
									yActual = actualClass.getObjectPositions()
											.getY()
											+ actualClass.getObjectPositions()
													.getHeight() + decal;
									yLinked = classLinked.getObjectPositions()
											.getY()
											+ classLinked.getObjectPositions()
													.getHeight() + decal;

									coefYActual = -1;
									coefYLinked = 1;
								}

								g.drawLine(xActual, yActual, xActual, yActual
										- 30 * coefYActual);
								g.drawLine(xActual, yActual - 30 * coefYActual,
										xLinked, yLinked + 20 * coefYLinked);

								if (links.get(k).toString().contains("extends")) {
									g.drawLine(xLinked - 10, yLinked + 20
											* coefYLinked, xLinked + 10,
											yLinked + 20 * coefYLinked);
									g.drawLine(xLinked - 10, yLinked + 20
											* coefYLinked, xLinked, yLinked + 1
											* coefYLinked);
									g.drawLine(xLinked + 10, yLinked + 20
											* coefYLinked, xLinked, yLinked + 1
											* coefYLinked);
								}
							} else {
							}
						}
					}
				}
			}

		}

		g.setColor(Color.black);
		// Boucle pour dessiner les packages
		for (int i = 0; i < program.getAllPackages().size(); i++) {
			ObjectPackage actualPackage = program.getAllPackages().get(i);
			posInitX = 50;
			xInit = posInitX;
			yFinal = 0;
			// Boucle pour dessiner les classes
			for (int j = 0; j < actualPackage.getAllClass().size(); j++) {
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				int width = actualClass.getObjectPositions().getWidth();
				int height = actualClass.getObjectPositions().getHeight();
				yTitle = 16;
				yAttribut = 22;
				actualClass.getObjectPositions().setX(posInitX);
				actualClass.getObjectPositions().setY(posInitY);
				g.setColor(Color.white);
				g.fillRect(posInitX, posInitY, width, height);
				g.setColor(Color.black);
				// Rectangle du titre
				if (actualPackage.getAllClass().get(j) instanceof ObjectAbstractClass) {
					height += 12;
					yTitle += 12;
					yAttribut += 12;
					g.setColor(Color.white);
					g.fillRect(posInitX, posInitY, width, height);
					g.setColor(Color.black);
					g.drawString("<<abstract>>", posInitX + ((width / 2) - 38),
							posInitY + 12);

				} else {
					g.setColor(Color.white);
					g.fillRect(posInitX, posInitY, width, height);
					g.setColor(Color.black);
				}
				g.drawRect(posInitX, posInitY, width, yTitle);
				// Rectangle des attributs
				g.drawRect(posInitX, posInitY, width, yAttribut
						+ (12 * actualClass.getAttributes().size()));
				// Rectangle des méthodes
				g.drawRect(posInitX, posInitY, width, height);

				// Affiche le titre de la classe
				g.drawString(actualClass.getName(), posInitX
						+ ((width / 2) - actualClass.getName().length() * 4),
						posInitY + yTitle - 4);

				// On écrit tout les attributs
				for (int k = 0; k < actualClass.getAttributes().size(); k++) {
					// Ajouté le deuxième carré qui est égale à la largeur de la
					// classe et 10*le nombre d'attributs d'hauteur.
					g.drawString(actualClass.getAttributes().get(k).toString(),
							posInitX + 3, (posInitY + yTitle + (12 * (k + 1))));
				}

				// On écrit toute les méthodes
				for (int l = 0; l < actualClass.getMethods().size(); l++) {
					g.drawString(
							actualClass.getMethods().get(l).stringForDraw(),
							posInitX + 3,
							(posInitY + yAttribut
									+ (actualClass.getAttributes().size() * 12) + (12 * (l + 1))));
				}
				g.setColor(Color.black);
				posInitX = posInitX + width + 50;
				xFinal = posInitX - 40;
				if (yFinal <= height + 20) {
					yFinal = height + 20;
				}
			}
			for (int j = 0; j < actualPackage.getAllInterface().size(); j++) {

				ObjectInterface actualInterface = actualPackage
						.getAllInterface().get(j);
				int width = actualInterface.getObjectPositions().getWidth();
				int height = actualInterface.getObjectPositions().getHeight();
				yTitle = 28;
				yAttribut = 34;
				actualInterface.getObjectPositions().setX(posInitX);
				actualInterface.getObjectPositions().setY(posInitY);

				// Rectangle du titre
				height += 12;
				g.setColor(Color.white);
				g.fillRect(posInitX, posInitY, width, height);
				g.setColor(Color.black);
				g.drawString("<<Interface>>", posInitX + ((width / 2) - 38),
						posInitY + 12);

				g.drawRect(posInitX, posInitY, width, yTitle);
				// Rectangle des attributs
				g.drawRect(posInitX, posInitY, width, yAttribut);
				// Rectangle des méthodes
				g.drawRect(posInitX, posInitY, width, height);

				// Affiche le titre de la classe
				g.drawString(actualInterface.getName(),
						posInitX
								+ ((width / 2) - actualInterface.getName()
										.length() * 3), posInitY + yTitle - 4);

				// On écrit toutes les méthodes
				for (int l = 0; l < actualInterface.getAbstractMethods().size(); l++) {
					g.drawString(actualInterface.getAbstractMethods().get(l)
							.stringForDraw(), posInitX + 3, (posInitY
							+ yAttribut + (12 * (l + 1))));
				}
				g.setColor(Color.black);
				posInitX = posInitX + width + 50;
				xFinal = posInitX - 40;
				if (yFinal <= height + 20) {
					yFinal = height + 20;
				}

			}

			// xFinal += 5;
			if (actualPackage.getAllClass().size() != 0
					|| actualPackage.getAllInterface().size() != 0) {
				g.setColor(Color.green);
				g.drawRect(xInit - 15, posInitY - 15, xFinal - 30, yFinal);
				g.drawString(actualPackage.getName(), xInit + 2, posInitY - 4);
				g.setColor(Color.black);
			}
			posInitY += yFinal + 50;
		}

		/**
		 * Les links ne sont affichées qu'une fois tous les packages, et donc
		 * toutes les classes, sont dessinées.
		 */

		// g.drawRect(0, 0, 100, 100);
	}
}