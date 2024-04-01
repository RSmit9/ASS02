import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame implements ActionListener
    {
        private JTextField productIdField, nameField, desField, costField, recordCountField;
        private JButton addButton;
        private FileWriter fileWriter;
        private int recordCount;

        public RandProductMaker()
            {
                setTitle("Random Product Maker");
                setSize(600, 350);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(EXIT_ON_CLOSE);

                JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

                panel.add(new JLabel("Product ID:"));
                productIdField = new JTextField();
                panel.add(productIdField);

                panel.add(new JLabel("Product Name:"));
                nameField = new JTextField();
                panel.add(nameField);

                panel.add(new JLabel("Short description:"));
                desField = new JTextField();
                panel.add(desField);

                panel.add(new JLabel("Cost:"));
                costField = new JTextField();
                panel.add(costField);

                panel.add(new JLabel("Record Count:"));
                recordCountField = new JTextField();
                recordCountField.setEditable(false);
                panel.add(recordCountField);

                addButton = new JButton("Add");
                addButton.addActionListener(this);
                panel.add(addButton);

                add(panel);
            }

            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == addButton)
                    {
                        if (validateFields())
                            {
                                writeRecord();
                                clearFields();
                                updateRecordCount();
                            }
                        else
                            {
                                JOptionPane.showMessageDialog(this, "Double Check data", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                    }
            }

            private boolean validateFields()
                {
                    String productId = productIdField.getText().trim();
                    String name = nameField.getText().trim();
                    String price = costField.getText().trim();
                    String quantity = costField.getText().trim();

                    return !productId.isEmpty() && !name.isEmpty() && isDouble(price) && isInteger(quantity);
                }

            private boolean isDouble(String s)
                {
                    try
                        {
                            Double.parseDouble(s);
                            return true;
                        }
                    catch (NumberFormatException e)
                        {
                            return false;
                        }
                }

            private boolean isInteger(String s)
                {
                    try
                        {
                            Integer.parseInt(s);
                            return true;
                        }
                    catch (NumberFormatException e)
                        {
                            return false;
                        }
                }

            private void writeRecord()
                {
                    try
                        {
                            fileWriter = new FileWriter("products.txt", true); // Append mode
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                            StringBuilder record = new StringBuilder();
                            record.append(padField(productIdField.getText(), 6)).append("|");
                            record.append(padField(nameField.getText(), 35)).append("|");
                            record.append(padField(desField.getText(), 75)).append("|");
                            record.append(costField.getText());

                            bufferedWriter.write(record.toString());
                            bufferedWriter.newLine();

                            bufferedWriter.close();
                            fileWriter.close();
                        }
                    catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                }

            private String padField(String field, int length)
                {
                    if (field.length() < length)
                        {
                            StringBuilder paddedField = new StringBuilder(field);
                            while (paddedField.length() < length)
                                {
                                    paddedField.append(" ");
                                }
                            return paddedField.toString();
                        }
                    else
                        {
                            return field.substring(0, length);
                        }
                }

            private void clearFields()
                {
                    productIdField.setText("");
                    nameField.setText("");
                    desField.setText("");
                    costField.setText("");
                }

            private void updateRecordCount()
                {
                    recordCount++;
                    recordCountField.setText(Integer.toString(recordCount));
                }

            public static void main(String[] args)
                {
                    SwingUtilities.invokeLater(() ->
                        {
                            RandProductMaker app = new RandProductMaker();
                            app.setVisible(true);
                        });
                }
    }
