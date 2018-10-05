import java.util.*;
/**
 * 
 * @author �½��� 2017192038
 * @version  2018-09 (4.9.0)
 */
public class lushi {
	public static void main(String []argd) {
		Player man[];
		man=new Player[2];
		man[0]=new Player();
		man[1]=new Player();
		Scanner reader=new Scanner(System.in);
		
		int n,position,attack,health;
		int now=0,next=1,winner=0;
		int attackPosition,defendPosition;
		String action;
		
		n=reader.nextInt();
		
		for(int j=0;j<n;j++) {
			action=reader.next();
			//System.out.println(action);
			if(action.equals("summon")) {
				position=reader.nextInt();
				attack=reader.nextInt();
				health=reader.nextInt();
				man[now].summon(position, attack, health);
				//System.out.println("man["+now+"]  "+man[now].followNum);
			}
			else if(action.equals("attack")) {
				attackPosition=reader.nextInt();
				defendPosition=reader.nextInt();
				man[now].master[attackPosition-1].attack(man[next],defendPosition);
				if(man[now].master[attackPosition-1].health<=0) {
					man[now].followDie(attackPosition);
				}
				if(defendPosition!=0) {
					if(man[next].master[defendPosition-1].health<=0) {
						man[next].followDie(defendPosition);
				}
				}
				else {
					if(man[next].health<=0) {
					if(now==0) {
						winner=1;
					}
					else {
						winner=-1;
					}
						
				}
			}
				}
				
			else if(action.equals("end")){
				int temp=now;
				now=next;
				next=temp;
				if(winner!=0)
				break;
			}
			
		}
		
		System.out.println(winner);
		System.out.println(man[0].health);
		if(man[0].followNum==0) {
			System.out.println(0);
		}
		else {
			System.out.print(man[0].followNum+" ");
			for(int i=0;i<7;i++) {
			if(man[0].master[i].health!=0) {
				System.out.print(man[0].master[i].health);
				man[0].followNum--;
				if(man[0].followNum!=0) {
					System.out.print(" ");
				}
			}
		}
			System.out.println();
		}
	
		System.out.println(man[1].health);
		if(man[1].followNum==0) {
			System.out.print(0);
		}
		else {
			System.out.print(man[1].followNum+" ");
			for(int i=0;i<7;i++) {
			if(man[1].master[i].health!=0) {
				System.out.print(man[1].master[i].health);
				man[1].followNum--;
				if(man[1].followNum!=0)
					System.out.print(" ");
			}
			if(man[1].followNum==0) {
				break;
			}
		}
		}
	}
}
/**
 * �����
 */
class Follow{
	int health,attack,position;
	/**
	 * ���ȱʡ���캯��
	 */
	Follow(){
		health=0;
		attack=0;
		position=0;
	}
	/**
	 * �����������
	 */
	public void reSet() {
		health=0;
		attack=0;
		position=0;
	}
	/**
	 * ��ӹ��캯��
	 * �ٻ���ӻ���ø÷���
	 * @param p ��ӱ��
	 * @param a	��ӹ�����
	 * @param h �������ֵ
	 */
	Follow(int p,int a,int h){
		position=p;
		attack=a;
		health=h;
	}
	/**
	 * ��ӹ�������
	 * @param t ���
	 * @param defendPosition ��������λ�ã�0-7��
	 */
	public void attack(Player t,int defendPosition) {
		if(defendPosition!=0) {
			t.master[defendPosition-1].health-=attack;
			health-=t.master[defendPosition-1].attack;		
		}
		else {
			t.health-=attack;
		}
	}
}
/**
 * �����
 * �ں�һ���������	
 */
class Player{
	int health,attack;
	int followNum;
	Follow master[];
	/**
	 * �����ȱʡ���캯��
	 */
	Player(){
		health=30;
		attack=0;
		followNum=0;
		master=new Follow[7];
		for(int i=0;i<7;i++) {
			master[i]=new Follow();
					}
	}
	/**
	 * ����ٻ�����
	 * �����������е���ӹ��캯��Follow()
	 * @param p	��ӱ��
	 * @param a	��ӹ�����
	 * @param h	�������ֵ
	 */
	public void summon(int p,int a,int h) {
		followNum++;
		for(int i=5;i>=p-1;i--) {
			if(master[i].position!=0) {
				master[i].position++;
			}
			master[i+1].attack=master[i].attack;
			master[i+1].health=master[i].health;
			master[i+1].position=master[i].position;
		}
		master[p-1].reSet();
		master[p-1].position=p;
		master[p-1].attack=a;
		master[p-1].health=h;
	}
	/**
	 * �����������
	 * ��������������������ұߵ���ӱ�ż�һ
	 * �����reSet����
	 * @param diePosition ������ӵı��
	 */
	public void followDie(int diePosition) {
			followNum--;
		for(int i=diePosition;i<7;i++) {
			if(master[i].position!=0) {
				master[i].position--;
			}
			master[i-1].attack=master[i].attack;
			master[i-1].health=master[i].health;
			master[i-1].position=master[i].position;
		}
		master[6].reSet();
	}
}