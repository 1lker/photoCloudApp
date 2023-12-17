/**
 * The CommentPanel class represents a panel for displaying and adding comments.
 * It provides a text area for entering new comments and a button to add them.
 * The comments are displayed in a scrollable area along with a count of the total comments.
 * 
 * Usage:
 * CommentPanel commentPanel = new CommentPanel(comments);
 * 
 * Parameters:
 * - comments: A List of String objects representing the initial comments.
 * 
 * Example:
 * List<String> comments = new ArrayList<>();
 * comments.add("Great photo!");
 * comments.add("Nice composition!");
 * CommentPanel commentPanel = new CommentPanel(comments);
 * 
 * Adding a comment:
 * 1. Enter the comment in the text area.
 * 2. Click the "Add Comment" button.
 * 3. The comment will be added to the list of comments and displayed in the comment panel.
 * 
 * Note:
 * - The comments list passed to the CommentPanel constructor will be modified when a new comment is added.
 * - The CommentPanel extends JPanel and should be added to a container component for display.
 */
package photoCloudApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class CommentPanel extends JPanel {
    private JLabel commentCountLabel;
    private JTextArea commentTextArea;
    private JButton addCommentButton;
    private List<String> comments;
    
    /**
     * Constructs a CommentPanel with the specified initial comments.
     * 
     * @param comments A List of String objects representing the initial comments.
     */
    public CommentPanel(List<String> comments) {
        this.comments = comments;
        initComponents();
    }
    
    /**
     * Initializes the components of the CommentPanel.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Comment Count Label
        commentCountLabel = new JLabel("Comments (" + comments.size() + ")");
        commentCountLabel.setFont(new Font(commentCountLabel.getFont().getName(), Font.BOLD, 16));
        commentCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(commentCountLabel, BorderLayout.NORTH);
        
        // Comment Text Area
        commentTextArea = new JTextArea();
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(commentTextArea);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add Comment Button
        addCommentButton = new JButton("Add Comment");
        addCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newComment = commentTextArea.getText().trim();
                if (!newComment.isEmpty()) {
                    comments.add(newComment);
                    commentCountLabel.setText("Comments (" + comments.size() + ")");
                    commentTextArea.setText("");
                    JOptionPane.showMessageDialog(CommentPanel.this, "Comment added successfully!");
                } else {
                    JOptionPane.showMessageDialog(CommentPanel.this, "Please enter a comment.");
                }
            }
        });
        add(addCommentButton, BorderLayout.SOUTH);
    }
}
