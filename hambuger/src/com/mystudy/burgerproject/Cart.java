package com.mystudy.burgerproject;

public class Cart {
	
		int count; //��ٱ��� ���ü���
		int total; //��ٱ��� �հ�
		BurgerVO bvo;
		DrinkVO dkvo;
		DessertVO dsvo;
		SetVO setvo;
		
		
		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		Cart(){
			this.count = count;
			this.total =
				(int)(bvo.getBgId() + dkvo.getDkId()
						+ dsvo.getDsId()
						+setvo.getSetId())*count;

		}

		@Override
		public String toString() {
			return "Cart [count=" + count + ", total=" + total + ","
					+ " bvo=" + bvo + ", dkvo=" + dkvo + ", dsvo=" + dsvo
					+ ", setvo=" + setvo + "]";
		}
		
		
		
	}


