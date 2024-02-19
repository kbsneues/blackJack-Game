import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class KBS_GUI extends JFrame implements ActionListener {
    private JButton hitButton;
    private JButton standButton;
    private JLabel playerLabel;
    private JLabel dealerLabel;
    private JLabel resultLabel;
    
    public static ArrayList<String> my_card;
    public static ArrayList<String> dealer_card;
    public static ArrayList<Integer> my_cardsum; 
    public static ArrayList<Integer> dealer_cardsum;
	
    public static int chance = 0;
    public static int my_score, com_score;
    
    public static Random random = new Random(); // 랜덤함수 
	public static char[] card_shape = {(char)0x2660, (char)0x2663, (char)0x2665, (char)0x2666};  // ♠♣♥♦ 의 문자를 저장하는 배열
	public static String[] card_number = {"ACE","2","3","4","5","6","7","8","9","10","K","Q","J"}; // 카드의 이름을 저장하는 배열 
	public static int[] real_number = {11,2,3,4,5,6,7,8,9,10,10,10,10}; // 카드의 값을 저장하는 배열 
    
    public static ArrayList<String> all_card() { // 카드 2장을 받는다. 
		ArrayList<String> kbs_card = new ArrayList<>(); // 나의 카드 정보를 저장
		for(int i=0;i<2;i++) { // 플레이어, 상대방은 카드를 2장 받는다. 
			kbs_card.add(card_shape[random.nextInt(card_shape.length)] 
					+ card_number[random.nextInt(card_number.length)]); // 플레이어는 2장의 카드를 받는다. 
		}
		return kbs_card;
    }
    
    
    public static ArrayList<Integer> all_cardsum(ArrayList<String> kbs_card, int n) {
    	ArrayList<Integer> kbs_cardsum = new ArrayList<>(); // 나의 카드 값 정보를 저장 
    	
    	for(int i=0;i<n;i++) { // 플레이어, 상대방은 카드를 2장 받는다. 
			String st = kbs_card.get(i); // 카드 모양과 카드의 번호를 나타냄 (example : ♦5) 
			kbs_cardsum.add(real_number[Arrays.asList(card_number).indexOf(st.substring(1))]);
			} // 카드의 값을 나의 카드 값 정보 배열에 저장 
    	
    	return kbs_cardsum;
    	
    }
    
    public static int calculate_sum(ArrayList<Integer> kbs) { // 카드 값의 합을 반환하는 함수  
		int sum = 0;
		
		for(int i: kbs) 
			sum+= i;
		
		if (sum == 21 && kbs.size() == 2) // 블랙잭이 될 때 
			return sum;
		
		if (sum > 21 && kbs.contains(11) && kbs.size() >= 2) { // 카드의 개수가 2개 이상이고 ACE 카드를 포함하고 카드의 합이 21을 초과 할 때 
			for(int i=0;i<kbs.size();i++) {	// ACE 카드를 찾는다
				if(kbs.get(i) == 11) {
					kbs.set(i, 1);  // ACE의 값을 1로 바꾼다. 
					break;
					}
				}
				
			sum = 0;
				for(int i: kbs) 
					sum+=i;
		}
		
		return sum; // 카드의 합을 반환
	}
    
    public static String game_compare(int kbs, int com, ArrayList<String> kbs_arr, ArrayList<String> com_arr) { 
		if (kbs > 21 && com > 21) // 플레이어 bust 
			return "<게임 결과 : 패배 (이유 : 21을 초과) - 나의 현재 상태 : Bust!!>";
		
		if (kbs == com) // draw 
			return "<게임 결과 : 무승부(Draw)>";
		else if(com == 21 && com_arr.size() == 2) // 상대방이 블랙잭 
			return "<게임 결과 : 패배 (이유 : 상대방 블랙잭)>";
		else if(kbs == 21 && kbs_arr.size() == 2)  // 플레이어 블랙잭 
			return "<게임 결과 : 승리(이유: 플레이어 블랙잭)>";
		else if(kbs > 21) // 플레이어 bust 
			return "<게임 결과 : 패배(이유 : 21을 초과) - 나의 현재 상태 : Bust!!> ";
		else if(com > 21) // 상대방 bust 
			return "<게임 결과 : 승리(이유 : 상대방 21 초과) - 상대방의 현재 상태 : Bust!!> ";
		else if (kbs > com) // 플레이어 21에 더 가까움 
			return "<게임 결과 : 승리(이유 : 상대방보다 21에 더 가까움)>";
		else // 상대방 21에 더 가까움 
			return "<게임 결과 : 패배(이유 : 상대방이 21에 더 가까움)>";
	}
    
    	
    public KBS_GUI() {
        
        my_card = all_card();  // 플레이어 카드 2장 
        dealer_card = all_card(); // 딜러 카드 2장 
       
    	

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        playerLabel = new JLabel("Player: " + my_card);
        dealerLabel = new JLabel("Dealer: " + dealer_card.get(0));
        resultLabel = new JLabel("");

        hitButton.addActionListener(this);
        standButton.addActionListener(this);
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        JPanel labelPanel = new JPanel();
        labelPanel.add(playerLabel);
        labelPanel.add(dealerLabel);
        
        JPanel resultPanel = new JPanel();
        resultPanel.add(resultLabel);
 

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(labelPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel,BorderLayout.SOUTH);
        
        add(mainPanel);
        
        setTitle("BlackJackGame GUI");
        setSize(400, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	dealer_cardsum = all_cardsum(dealer_card,dealer_card.size());
		com_score = calculate_sum(dealer_cardsum);

        
        while (com_score !=0 && com_score < 17) { // 상대방은 카드의 합이 16이하이면 무조건 한 장의 카드를 받는다. 17이상인 경우 카드를 받지 않는다. 
			dealer_card.add(card_shape[random.nextInt(card_shape.length)] + card_number[random.nextInt(card_number.length)]); // 딜러는 카드를 받는다. 
			String kt = dealer_card.get(dealer_card.size()-1);
			dealer_cardsum.add(real_number[Arrays.asList(card_number).indexOf(kt.substring(1))]); // 카드의 값을 상대방(딜러) 카드 값 정보 배열에 저장 
			com_score = calculate_sum(dealer_cardsum);  // 상대방(딜러)의 최종적인 카드 값의 정보를 저장 
    	}
    	
        
    	if(com_score == 21 || my_score >= 21) {
    		resultLabel.setText(game_compare(my_score,com_score, my_card, dealer_card));
    	}
    	
    	else {
    		if(e.getSource() == hitButton && chance == 0) {
    			my_card.add(card_shape[random.nextInt(card_shape.length)] + card_number[random.nextInt(card_number.length)]); // 플레이어는 3장째 카드를 받는다.
            	playerLabel.setText("Player: " + my_card);
            	chance++;
            	my_cardsum = all_cardsum(my_card,my_card.size()); // 플레이어 카드 값 
            	my_score = calculate_sum(my_cardsum);
    		}
    	}
    	
    	if (e.getSource() == standButton) {
    		System.out.println("플레이어 최종 카드 : "+ my_card + ", 딜러 최종 카드 : "  + dealer_card);
    		my_cardsum = all_cardsum(my_card,my_card.size()); // 플레이어 카드 값 
    		dealer_cardsum = all_cardsum(dealer_card,dealer_card.size());
    		my_score = calculate_sum(my_cardsum);
    		com_score = calculate_sum(dealer_cardsum);
    		resultLabel.setText(game_compare(my_score,com_score, my_card, dealer_card));
        }
    }

    public static void main(String[] args) {
    	KBS_GUI kbs = new KBS_GUI();
    }
}
