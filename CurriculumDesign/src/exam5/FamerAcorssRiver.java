package exam5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FamerAcorssRiver
{
    public ArrayList<ArrayList<int[]>> findSolutions()
    {
        // ������������
        ArrayList<ArrayList<int[]>> solutions = new ArrayList<ArrayList<int[]>>();
        // �����Ѿ��������
        ArrayList<int[]> haveAcess = new ArrayList<int[]>();
        // ����״̬��
        Queue<int[]> queueState = new LinkedList<int[]>();
        // ��ʼ��״̬ ũ���ǡ��ײˡ��� 0 ���ϰ���1 �ڱ���
        int[] start = {0, 0, 0, 0};
        // ��־�Ƿ�ʼ��֧
        boolean isBranch = false;

        solutions.add(new ArrayList<int[]>());
        queueState.add(start);  // �ѵ�һ�����������
        haveAcess.add(start);   // ������к󣬱����Ѿ����ʸ�״̬

        while (!queueState.isEmpty())
        {
            ArrayList<int[]> current = new ArrayList<int[]>();  // ��¼��ǰ��״̬���ü�����һ����·��
            int size = queueState.size();
            while (!queueState.isEmpty())
            {
                int[] temp = queueState.remove();
                if (!toString(temp).equals("1111"))
                    current.add(temp);

                if (size == 1 && !isBranch) // ��û�з�֧ʱ
                    solutions.get(0).add(temp);
                else if (size != 1 && !isBranch)    // �������ʼ���з�֧
                {
                    solutions.add(new ArrayList<int[]>());
                    listCopy(solutions.get(0), solutions.get(1));   // ��ǰ����ͬ�Ķ����ƽ��µ�
                    solutions.get(0).add(temp);
                    int[] newTemp = queueState.remove();// ȡ���ڶ���֧·
                    if (!toString(newTemp).equals("1111"))
                        current.add(newTemp);
                    solutions.get(1).add(newTemp);
                    isBranch = true;
                }else if (size != 1 && isBranch)
                {
                    solutions.get(0).add(temp);
                    int[] temp1 = queueState.remove();
                    if (!toString(temp1).equals("1111"))
                        current.add(temp1);
                    solutions.get(1).add(temp1);
                }else if (size == 1 && isBranch)
                {
                    solutions.get(0).add(temp);
                    solutions.get(1).add(temp);
                }
            }

            for (int i = 0; i < current.size(); i++)
            {
                // ��ȡ��һ�����������
                Queue<int[]> next = getNext(current.get(i));
                while (!next.isEmpty())
                {
                    int[] temp = next.remove();
                    // �ж��Ƿ�ȫ���Լ��Ƿ��ѱ�����
                    if (isSafe(temp) && !contains(haveAcess, temp))
                    {
                        queueState.add(temp);
                        haveAcess.add(temp);
                    }else if (!contains(haveAcess, temp))
                    {
                        haveAcess.add(temp);
                    }
                }
            }
        }

        return solutions;
    }

    /**
     * ����һ��list
     * @param src
     * @param pos
     * @return
     */
    private ArrayList<int[]> listCopy(ArrayList<int[]> src, ArrayList<int[]> pos)
    {
        for (int i = 0; i < src.size(); i++)
        {
            pos.add(src.get(i));
        }
        return pos;
    }

    /**
     * �ж����Ѿ����ʹ����Ƿ��ڴ˱�����
     * @param hasAcess
     * @param state
     * @return
     */
    private boolean contains(ArrayList<int[]> hasAcess, int[] state)
    {
        for (int i = 0; i < hasAcess.size(); i++)
        {
            if (toString(hasAcess.get(i)).equals(toString(state)))
                return true;
        }

        return false;
    }

    /**
     * �ж�һ��״̬�Ƿ�ȫ
     * 1100 ��Ͱײ�ͬʱ���ϰ�
     * 1010 �Ǻ���ͬʱ���ϰ�
     * 0101 �Ǻ���ͬʱ�ڱ���
     * 0011 ��Ͱײ�ͬʱ�ڱ���
     * @param state
     * @return
     */
    private boolean isSafe(int[] state)
    {
//        String temp = toString(state);
//        System.out.println(temp);
        if (state.length != 4)
        {
            System.out.println("���鳤�Ȳ���ȷ");
            return false;
        }else if ((state[1] == state[3] && state[1] != state[0])
                    || (state[2] == state[3] && state[2] != state[0]))
        {
            return false;
        }

        return true;
    }

    /**
     * ������ת���ɶ�Ӧ�ַ���
     * @param state
     * @return
     */
    private String toString(int[] state)
    {
        String result = "";
        for (int i = 0; i < state.length; i++)
        {
            result += state[i];
        }

        return result;
    }

    /**
     * ��ȡ��һ����ȫ�����ܣ������Ƿ�ȫ
     * @param state
     * @return
     */
    private Queue<int[]> getNext(int[] state)
    {
        Queue<int[]> queue = new LinkedList<int[]>();
        for (int i = 0; i < state.length; i++)
        {
            // �����Ʒ��ũ����ͬһ��
            if (state[0] == state[i])
            {
                queue.add(passRiver(i, state));
            }
        }

        return queue;
    }

    /**
     * ��ָ���±괦�Ķ�������
     * @param index
     * @param state
     * @return
     */
    private int[] passRiver(int index, int[] state)
    {
        int[] newState = new int[state.length];
        System.arraycopy(state, 0, newState, 0, state.length);
        if (state[0] == 0)  // ���ũ�����ϰ�
        {
            newState[0] = 1;
            newState[index] = 1;   // �������Ʒ��������
        }else   // ����ũ���ڱ���
        {
            newState[0] = 0;
            newState[index] = 0;   // �������Ʒ�����ϰ�
        }

        return newState;
    }

//    private static String describe(int[] state)
//    {
//        for (int i = 0; i < state.length; i++)
//        {
//            switch (i)
//            {
//                case 0:
//            }
//        }
//    }

    public static void main(String[] args)
    {
        FamerAcorssRiver s = new FamerAcorssRiver();
        ArrayList<ArrayList<int[]>> solutions = s.findSolutions();
        for (int i = 0; i < solutions.size(); i++)
        {
            System.out.println("������" + (i + 1));
            System.out.printf("%-15s%-25s%-15s\n", "����", "�ϰ�", "����");
            for (int j = 0; j < solutions.get(i).size(); j++)
            {
                String south = "";
                String north = "";
                for (int k = 0; k < solutions.get(i).get(j).length; k++)
                {
                    switch (k)
                    {
                        case 0:
                            if (solutions.get(i).get(j)[0] == 0)
                                south += "ũ��";
                            else
                                north += "ũ��";
                            break;
                        case 1:
                            if (solutions.get(i).get(j)[1] == 0)
                                south += " ��";
                            else
                                north += " ��";
                            break;
                        case 2:
                            if (solutions.get(i).get(j)[2] == 0)
                                south += " �ײ�";
                            else
                                north += " �ײ�";
                            break;
                        case 3:
                            if (solutions.get(i).get(j)[3] == 0)
                                south += " ��";
                            else
                                north += " ��";
                            break;
                    }
                }
                String step = "��" + (j+1) + "��";
                System.out.printf("%-15s%-25s%-15s\n", step, south, north);
            }
            System.out.println();
        }
    }
}