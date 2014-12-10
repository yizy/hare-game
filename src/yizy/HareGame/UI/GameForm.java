package yizy.HareGame.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import yizy.HareGame.Board;
import yizy.HareGame.Hare;
import yizy.HareGame.HareGame;
import yizy.HareGame.HareGame.RoleName;
import yizy.HareGame.HareGameEvent;
import yizy.HareGame.HareGameEvent.SuccessType;
import yizy.HareGame.HareGameException;
import yizy.HareGame.HareGameListener;
import yizy.HareGame.Hound;
import yizy.HareGame.Role;
import yizy.HareGame.Square;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class GameForm implements HareGameListener, FastBrainListener, ItemListener {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {
			// handle exception
            e.printStackTrace();
		}
        
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameForm window = new GameForm();
					window.frmHareAndHounds.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHareAndHounds = new JFrame();
		frmHareAndHounds.setTitle("Hare and Hounds Game");
        frmHareAndHounds.setResizable(false);
		frmHareAndHounds.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit=frmHareAndHounds.getToolkit();
		Dimension winSize=kit.getScreenSize();
		frmHareAndHounds.setBounds(winSize.width/4,winSize.height/4, 866, 378);
		
		JPanel panelGame = new JPanel() {
			private static final long serialVersionUID = 4733334348977661865L;

			protected void paintComponent(Graphics g) {
                ImageIcon icon;
                try {
                	icon = new ImageIcon(glassImgUrl);
                } catch (Exception e) {
                	icon = new ImageIcon(glassImgAbsUrl);
                }
                
				Image imgBack = icon.getImage();
				g.drawImage(imgBack, 0, 0, 500, 300, icon.getImageObserver());
			}
		};
		panelGame.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelGame.setLayout(null);
        
        makeBoardButtons(panelGame, 100, 60);
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel = new JPanel();
		
		textStatus = new JTextArea(8, 18);
		textStatus.setBackground(SystemColor.window);
		textStatus.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textStatus);
		
		
		GroupLayout groupLayout = new GroupLayout(frmHareAndHounds.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelGame, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelGame, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panel, 0, 0, Short.MAX_VALUE)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap())
		);
		
		JButton btnReplay = new JButton("Replay");
		btnReplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                initGame();
			}
		});
		btnReplay.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnReplay);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                frmHareAndHounds.dispose();
			}
		});
		btnClose.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnClose);
		
		JLabel lblRobot = new JLabel("Robot");
		panel_1.add(lblRobot);
		
		cboxRobots = new Choice();
		panel_1.add(cboxRobots);
		
		JLabel lblLevel = new JLabel("Level");
		panel_1.add(lblLevel);
		
		cboxLevels = new Choice();
		panel_1.add(cboxLevels);
		cboxLevels.addItem("Easy");
		cboxLevels.addItem("Median");
		cboxLevels.addItem("Difficult");
		cboxLevels.select(1);
		cboxLevels.addItemListener(this);
		cboxRobots.addItem("Hounds");
		cboxRobots.addItem("Hare");
        cboxRobots.addItem("None");
        cboxRobots.select(1);
		cboxRobots.addItemListener(this);
        
		frmHareAndHounds.getContentPane().setLayout(groupLayout);
        
		try {
			imgs[0] = new ImageIcon(houndImgUrl);
		} catch (Exception e) {
        	imgs[0] = new ImageIcon(houndImgAbsUrl);
		}
		try {
			imgs[1] = new ImageIcon(hareImgUrl);
		} catch (Exception e) {
        	imgs[1] = new ImageIcon(hareImgAbsUrl);
		}
        
		initGame();
	}
	
    // helpers
	protected void createGameButton(JPanel panel, int row, int col, int space, int size) {
        int x = space * row + (space-size) / 2;
        int y = space * col + (space-size) / 2;
		btns[row][col] = new JGameButton();
		btns[row][col].setBounds(y, x, size, size);
        btns[row][col].setPreferredSize(new Dimension(size, size));
        btns[row][col].setSquare(board.getSquare(row, col));
        createButtonListener(btns[row][col]);
        panel.add(btns[row][col]);
	}
	
	protected void makeBoard() {
        board = new Board(3, 5);
        board.addSquare(1, 0);
        board.addSquare(0, 1);
        board.addSquare(1, 1);
        board.addSquare(2, 1);
        board.addSquare(0, 2);
        board.addSquare(1, 2);
        board.addSquare(2, 2);
        board.addSquare(0, 3);
        board.addSquare(1, 3);
        board.addSquare(2, 3);
        board.addSquare(1, 4);
        
        board.makeConnection(1, 0, 0, 1);
        board.makeConnection(1, 0, 1, 1);
        board.makeConnection(1, 0, 2, 1);
        
        board.makeConnection(0, 1, 1, 1);
        board.makeConnection(1, 1, 2, 1);
        
        board.makeConnection(0, 1, 0, 2); 
        board.makeConnection(0, 1, 1, 2); 
        board.makeConnection(1, 1, 1, 2); 
        board.makeConnection(2, 1, 1, 2); 
        board.makeConnection(2, 1, 2, 2); 
        
        board.makeConnection(0, 2, 1, 2);
        board.makeConnection(1, 2, 2, 2);
        
        board.makeConnection(0, 2, 0, 3); 
        board.makeConnection(1, 2, 0, 3); 
        board.makeConnection(1, 2, 1, 3); 
        board.makeConnection(1, 2, 2, 3); 
        board.makeConnection(2, 2, 2, 3); 
        
        board.makeConnection(0, 3, 1, 3);
        board.makeConnection(1, 3, 2, 3);
        
        board.makeConnection(1, 4, 0, 3);
        board.makeConnection(1, 4, 1, 3);
        board.makeConnection(1, 4, 2, 3);
	}
    
    protected void makeBoardButtons(JPanel panel, int space, int size) {
        makeBoard();
        
        btns = new JGameButton[3][5];
        createGameButton(panel, 1, 0, space, size);
        createGameButton(panel, 0, 1, space, size);
        createGameButton(panel, 1, 1, space, size);
        createGameButton(panel, 2, 1, space, size);
        createGameButton(panel, 0, 2, space, size);
        createGameButton(panel, 1, 2, space, size);
        createGameButton(panel, 2, 2, space, size);
        createGameButton(panel, 0, 3, space, size);
        createGameButton(panel, 1, 3, space, size);
        createGameButton(panel, 2, 3, space, size);
        createGameButton(panel, 1, 4, space, size);
    	
    }
	
    protected void createButtonListener(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                if (isRobotWorking) 
                	appendTextStatus("> robot is working.\n");
                JGameButton btn = (JGameButton)arg0.getSource();
                Square square = btn.getSquare();
                if (square.isOccupied()) {
                    try {
                        Role role = game.findRoleByPosition(square);
                    	players[current].selectRole(role);
                    } catch (HareGameException exception) {
                        appendTextStatus("> can't move this one.\n");
                    }
                } else {
                    if (players[current].hasSelected()) {
                    	try {
                    		players[current].moveTo(square);
                    	} catch (HareGameException exception) {
                    		appendTextStatus("> can't move here.\n");
                    	}
                    }
                }
			}
		});
    }
    
    protected void drawGameButton(JButton from, JButton to, ImageIcon img) {
    	if (from != null) {
            from.setIcon(null);
    	}
        
        if (to != null) {
            to.setIcon(img);
        }
    }
	
    protected void setGameButtonEnable(boolean enable) {
        for (int i = 0; i < btns.length; ++i) {
        	for (int j = 0; j < btns[0].length; ++j) {
        		if (btns[i][j] != null) {
                    btns[i][j].setEnabled(enable);
        		}
        	}
        }
    }
    
    protected JGameButton getFocusedGameButton() {
        for (int i = 0; i < btns.length; ++i) {
        	for (int j = 0; j < btns[0].length; ++j) {
        		if (btns[i][j] != null && btns[i][j].isFocusOwner()) {
                    return btns[i][j];
        		}
        	}
        }
        return null;
    }
    
    protected void setAutoPlayer() {
    	if (cboxRobots.getSelectedItem() == "Hare") {
    		players[0].setAuto(false);
    		players[1].setAuto(true);
    	} else if (cboxRobots.getSelectedItem() == "Hounds") {
    		players[0].setAuto(true);
    		players[1].setAuto(false);
    	} else {
    		players[0].setAuto(false);
    		players[1].setAuto(false);
    	}
    	if (players[current].isAuto())
    		try {
    			players[current].newTurn(game, gameLevel, this);
    		} catch (CloneNotSupportedException e) {

    		}
    }
    
	protected void initGame() {
        for (int i = 0; i < btns.length; ++i) {
        	for (int j = 0; j < btns[0].length; ++j) {
        		if (btns[i][j] != null) {
        			btns[i][j].setIcon(null);
        			Square square = btns[i][j].getSquare();
        			square.setOccupied(false);
        		}
        	}
        }
    	
        Role hare = new Hare(btns[1][4].getSquare());
        drawGameButton(null, btns[1][4], imgs[1]);
        
        ArrayList<Role> hounds = new ArrayList<Role>();
        hounds.add(new Hound(btns[0][1].getSquare()));
        drawGameButton(null, btns[0][1], imgs[0]);
        hounds.add(new Hound(btns[1][0].getSquare()));
        drawGameButton(null, btns[1][0], imgs[0]);
        hounds.add(new Hound((Square)btns[2][1].getSquare()));
        drawGameButton(null, btns[2][1], imgs[0]);
        
        game = new HareGame(hare, hounds, true);
        game.setListener(this);
        
        gameLevel = gameLevels[cboxLevels.getSelectedIndex()];
        
        players[0] = new HoundPlayer(hounds, RoleName.HOUND, false);
        players[1] = new HarePlayer(hare, RoleName.HARE, false);
        current = 0;
        
        setAutoPlayer();
		textStatus.setText("> game start!!\n> hounds first.\n");
	}

    protected void appendTextStatus(String text) {
    	textStatus.append(text);
    	textStatus.selectAll();
    }
	
    // event handlers
	@Override
	public void onRoleMoved(HareGameEvent e) {
		Square fromSquare = e.getFrom();
		Square toSquare= e.getTo();
        
		JButton fromBtn = btns[fromSquare.getRow()][fromSquare.getColumn()];
		JButton toBtn = btns[toSquare.getRow()][toSquare.getColumn()];
        drawGameButton(fromBtn, toBtn, imgs[current]);
	}

	@Override
	public void onNextMoveReady(HareGameEvent e) {
        current = (current + 1) % 2;
        
        if (current == 0) {
        	appendTextStatus("\n> it's hounds' turn.\n");
        } else {
        	appendTextStatus("\n> it's hare's turn.\n");
        }
        
        try {
        	players[current].newTurn(game, gameLevel, this);
        } catch (CloneNotSupportedException exception) {
            appendTextStatus("> auto play failed.\n");
        }
		
	}
    
	@Override
	public void onSucceed(HareGameEvent e) {
        String successText = null;
        if (e.getSuccessType() == SuccessType.HARE_ESCAPE) {
            successText = "Hare escapes!!\n";
        	appendTextStatus("\n> Hare escapes!!\n");
        } else if (e.getSuccessType() == SuccessType.HOUNDS_STALL) {
            successText = "Hounds are stalling!!\n";
        	appendTextStatus("\n> Hounds are stalling!!\n");
        } else if (e.getSuccessType() == SuccessType.HOUNDS_TRAP) {
            successText = "Hounds trap the hare!!\n";
        	appendTextStatus("\n> Hounds trap the hare!!\n");
        }
        
		JOptionPane.showMessageDialog(null, successText,
				e.getWiner().toString() + " Win!", 
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void onFastBrainCompleted(FastBrainEvent e) {
        isRobotWorking = false;
        appendTextStatus("> max detph of game tree: ");
        appendTextStatus(String.valueOf(e.getMaxDetph()));
        appendTextStatus("\n> nodes generated: ");
        appendTextStatus(String.valueOf(e.getNodeCounter()));
        appendTextStatus("\n> pruning occurred within the MAX-VALUE: ");
        appendTextStatus(String.valueOf(e.getPruningCounterOfMaxVaule()));
        appendTextStatus("\n> pruning occurred within the MIN-VALUE: ");
        appendTextStatus(String.valueOf(e.getPruningCounterOfMinVaule()));
        appendTextStatus("\n");
        
        Square fromSquare = e.getFrom();
        Square toSquare = e.getTo();
        Role role = game.findRoleByPosition(fromSquare);
        
        try {
        	players[current].selectRole(role);
            players[current].moveTo(toSquare);
        } catch (HareGameException exception) {
        	appendTextStatus("> robot failed.\n");
        }
        
	}

	@Override
	public void onFastBrainStarted(FastBrainEvent e) {
		// TODO Auto-generated method stub
        isRobotWorking = true;
		appendTextStatus("> robot started...\n");
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == cboxLevels) {
			gameLevel = gameLevels[cboxLevels.getSelectedIndex()];
		} else if (arg0.getSource() == cboxRobots) {
            setAutoPlayer();
		}
	}

    // members
	private JFrame frmHareAndHounds;
    private ImageIcon[] imgs = new ImageIcon[2];
    private JGameButton[][] btns;
    private JTextArea textStatus;
    private Choice cboxLevels;
    private Choice cboxRobots;
    private URL glassImgUrl = getClass().getResource("/glass.png");
    private URL houndImgUrl = getClass().getResource("/hound.png");
    private URL hareImgUrl = getClass().getResource("/hare.png");
    private String glassImgAbsUrl = "img/glass.png";
    private String houndImgAbsUrl = "img/hound.png";
    private String hareImgAbsUrl = "img/hare.png";
    
    private HareGame game;
    private Board board;
    private int gameLevel;
    private int[] gameLevels = new int[]{3, 5, 11};
    private Player[] players = new Player[2];
    private int current;
    private boolean isRobotWorking;
}
