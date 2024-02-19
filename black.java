import java.util.*; 

public class black {
	
	private static int money = 1000; // 현재 잔액을 저장하는 변수 
	private static int bet = 0; // 베팅 금액을 저장하는 변수 
	
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
	
	// 카드 값의 합을 비교하여 게임 결과 값을 반환하는 함수 
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
	
	public static void play_game() { // 게임이 진행되는 함수 
		Random random = new Random(); // 랜덤함수 
		Scanner sc = new Scanner(System.in); // 스캐너 객체 
		char[] card_shape = {(char)0x2660, (char)0x2663, (char)0x2665, (char)0x2666};  // ♠♣♥♦ 의 문자를 저장하는 배열
		String[] card_number = {"ACE","2","3","4","5","6","7","8","9","10","K","Q","J"}; // 카드의 이름을 저장하는 배열 
		int[] real_number = {11,2,3,4,5,6,7,8,9,10,10,10,10}; // 카드의 값을 저장하는 배열 
		
		ArrayList<String> kbs_card = new ArrayList<>(); // 나의 카드 정보를 저장 
		ArrayList<Integer> kbs_cardsum = new ArrayList<>(); // 나의 카드 값 정보를 저장 
		
		ArrayList<String> com_card = new ArrayList<>(); // 상대방(딜러) 카드 정보를 저장 
		ArrayList<Integer> com_cardsum = new ArrayList<>(); // 상대방(딜러) 카드 값 정보를 저장 
		
		for(int i=0;i<2;i++) { // 플레이어, 상대방은 카드를 2장 받는다. 
			kbs_card.add(card_shape[random.nextInt(card_shape.length)] + card_number[random.nextInt(card_number.length)]); // 플레이어는 2장의 카드를 받는다. 
			String st = kbs_card.get(i); // 카드 모양과 카드의 번호를 나타냄 (example : ♦5) 
			kbs_cardsum.add(real_number[Arrays.asList(card_number).indexOf(st.substring(1))]); // 카드의 값을 나의 카드 값 정보 배열에 저장 
			
			com_card.add(card_shape[random.nextInt(card_shape.length)] + card_number[random.nextInt(card_number.length)]);  // 상대방은 2장의 카드를 받는다. 
			String kt = com_card.get(i); // 카드 모양과 카드의 번호를 나타냄 (example : ♠Q) 
			com_cardsum.add(real_number[Arrays.asList(card_number).indexOf(kt.substring(1))]); // 카드의 값을 상대방(딜러) 카드 값 정보 배열에 저장 
		}
		
		boolean start_game = true;
		while(start_game) { // 무한루프 
			
			int kbs_score = calculate_sum(kbs_cardsum); // 플레이어의 카드 값의 합을 저장하는 변수 
			int com_score = calculate_sum(com_cardsum); // 상대방의 카드 값의 합을 저장하는 변수 
			
			System.out.println("나의 카드 : " + kbs_card + " 나의 점수 : " + kbs_score);
			System.out.println("상대방의 카드 : " + com_card.get(0)); // 상대방의 카드는 플레이어가 한 장 밖에 보지 못한다. 
			
			if(com_score == 21 || kbs_score >= 21) { // 카드의 합이 21이상 이거나 플레이어가 블랙잭이 되는 경우이거나 상대방이 블랙잭이 되는 경우 
				if(kbs_score == 21) black.money =  black.bet * 2 + black.money; // 플레이어가 블랙잭이 될 때 베팅한 금액의 두배로 돈을 받는다 
				
				start_game = false; // 반복 종료 
			}
			else { // 카드의 합이 21보다 작을 때 
				System.out.print("카드를 더 받으시려면 hit,  카드를 받지 않으려면 stand 을 입력하시오. " + "(현재 잔액 : " + black.money + ")"+ " <answer> :  ");
				String user_select = sc.nextLine();
				if (user_select.equals("hit") && black.money >= black.bet) { // 더블 다운 
					black.money -= black.bet; // 이전에 베팅한 금액만큼 더 베팅한다.
					System.out.println("<더블다운을 하셨습니다. 그러므로 더 이상 카드를 받을 수 없습니다. (현재 잔액 : " + black.money + ")" + "  추가 베팅금액 : " + black.bet + ">" );
					kbs_card.add(card_shape[random.nextInt(card_shape.length)] + card_number[random.nextInt(card_number.length)]); // 플레이어는 3장째 카드를 받는다. 
					String st = kbs_card.get(kbs_card.size()-1); 
					kbs_cardsum.add(real_number[Arrays.asList(card_number).indexOf(st.substring(1))]); // 카드의 값을 나의 카드 값 정보 배열에 저장 
					start_game = false; // 더블 다운을 했으므로 더 이상 카드를 받지 않는다. 반복 종료 
				}
				else if(user_select.equals("stand")){ // 카드를 받지 않는다. 
					start_game = false; // 반복 종료 
				}
				else { // hit을 하였지만 베팅 금액이 현재 잔액보다 큰 경우 카드를 받지 않는다.  
					System.out.println("(베팅 금액 : " + black.bet + ", " + " 현재 잔액 : " + black.money + ")" + "  현재 잔액이 부족합니다. 카드를 받을 수 없습니다.");
					start_game = false; // 반복 종료 
				}
			}
		}
		
		int kbs_score = calculate_sum(kbs_cardsum);  // 플레이어의 카드 값의 합을 저장하는 변수 
		int com_score = calculate_sum(com_cardsum);  // 상대방의 카드 값의 합을 저장하는 변수 
		
		while (com_score !=0 && com_score < 17) { // 상대방은 카드의 합이 16이하이면 무조건 한 장의 카드를 받는다. 17이상인 경우 카드를 받지 않는다. 
			com_card.add(card_shape[random.nextInt(card_shape.length)] + card_number[random.nextInt(card_number.length)]); // 딜러는 카드를 받는다. 
			String kt = com_card.get(com_card.size()-1);
			com_cardsum.add(real_number[Arrays.asList(card_number).indexOf(kt.substring(1))]); // 카드의 값을 상대방(딜러) 카드 값 정보 배열에 저장 
			com_score = calculate_sum(com_cardsum);  // 상대방(딜러)의 최종적인 카드 값의 정보를 저장 
		}
		System.out.println("나의 최종 카드 : " + kbs_card + "  나의 최종 점수 : " + kbs_score); // 플레이어의 최종 카드 정보, 점수를 출력 
		System.out.println("상대방의 최종 카드 : " + com_card + "  상대방의 점수 : " + com_score); // 상대방의 최종 카드 정보, 점수를 출력 
		System.out.println(game_compare(kbs_score, com_score, kbs_card, com_card)); // 게임 결과를 출력 
	}
	
	public static void main(String[] args) { // main 함수 
		Scanner sc = new Scanner(System.in); 
		System.out.print("블랙잭 게임을 시작하시겠습니까? 1(시작) , 0(종료) " +  "<answer> : "); 
		int k = sc.nextInt();
		int i=1; // ROUND 숫자 표현하는 변수 
		while(k == 1) { // 1(시작)을 입력하였을 때 
			if (black.money == 0) break; // 현재 잔액이 0 일 때 게임을 종료한다. 
			System.out.print("얼마를 베팅하시겠습니까? " + "(현재 잔액 : " + black.money + ")" + "  베팅 금액 : ");
			black.bet = sc.nextInt(); // 베팅 금액 입력 
			if(black.money < black.bet || black.bet <= 0) break; // 베팅금액이 현재 잔액보다 크거나 베팅금액을 음수로 잘못 입력하였을 경우 블랙잭 게임을 종료한다.
			black.money -= black.bet; // 현재 잔액 업데이트 
			System.out.println("ROUND" + i  + "-START"+" -------------------------------------------------");
			play_game(); // 게임을 진행한다. 
			System.out.println("---------------------------------------------------- " + "ROUND" + i + "-END"); 
			i++;
		}
		System.out.println("블랙잭 게임을 종료합니다. " + (char)0x263A);
	}
}
