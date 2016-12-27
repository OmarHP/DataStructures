import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 26/12/2016.
 */
public class DisjointSet<E> {

    ArrayList<Node<E>> nodes;

    public DisjointSet(ArrayList<E> list){
        nodes=new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            nodes.add(new Node(i,list.get(i)));
        }
    }

    public int find(int i){
        Node ith=nodes.get(i);
        while(ith!=ith.parent){
            ith=nodes.get(find(ith.parent.id));
        }
        return ith.id;
    }


    public void union(int i, int j){
        int i_id=find(i);
        int j_id=find(j);

        if(i_id==j_id)
            return;

        if (nodes.get(i_id).getRank()>nodes.get(j_id).getRank()){
            nodes.get(j_id).setParent(nodes.get(i_id));
        }else{
            nodes.get(i_id).setParent(nodes.get(j_id));
            if(nodes.get(i_id).getRank()==nodes.get(j_id).getRank()){
                nodes.get(j_id).incrementRank();
            }
        }
    }

    private class Node<E>{
        private Node parent;
        private E value;
        private int rank;
        private int id;

        public Node(int id, E value){
            this.value=value;
            this.parent=this;
            this.id=id;
            rank=0;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }


        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public void incrementRank(){
            rank++;
        }
    }


    public static void main(String... args){
        ArrayList<Integer> list=new ArrayList<>();
        int n=20;
        for(int i=0; i<n; i++)
            list.add(i);

        DisjointSet<Integer> disjointSet = new DisjointSet<>(list);

        System.out.println("========== Before unions ===========");
        System.out.println(disjointSet.find(0));
        System.out.println(disjointSet.find(5));
        System.out.println(disjointSet.find(9));
        System.out.println(disjointSet.find(10));
        System.out.println(disjointSet.find(15));

        /*
        * State of set 1 after unions
        *       1
        * / / / /| \ \ \ \
        *0 2 3 4 5 6 7 8 9
        */

        disjointSet.union(0,1);
        disjointSet.union(1,2);
        disjointSet.union(2,3);
        disjointSet.union(3,4);
        disjointSet.union(4,5);
        disjointSet.union(5,6);
        disjointSet.union(6,7);
        disjointSet.union(3,8);
        disjointSet.union(9,5);

        disjointSet.union(15,19);

        /*
        Unions: (0 U 1), (1 U 2),
                (2 U 3), (3 U 4),
                (4 U 5), (5 U 6),
                (6 U 7), (3 U 8),
                (9 U 5), (15 U 19)

                /---- 0
               /----- 1
              /------ 2
             /------- 3
         1 <--------- 4
             \------- 5
              \------ 6
               \----- 7
                \---- 8
                 \--- 9

         10 <--- 10

         11 <--- 11

         12 <--- 12

         13 <--- 13

         14 <--- 14

         16 <--- 16

         17 <--- 17

         18 <--- 18

         19 <---- 19
              \-- 15

        */

        System.out.println("========== After unions ===========");
        for(int i=0; i<n; i++){
            System.out.println(disjointSet.find(i));
        }
    }

}
