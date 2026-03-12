import java.util.*;

class Solution {

    static class DSU {
        int[] parent, rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int a, int b) {
            int pa = find(a);
            int pb = find(b);

            if (pa == pb) return false;

            if (rank[pa] < rank[pb]) parent[pa] = pb;
            else if (rank[pb] < rank[pa]) parent[pb] = pa;
            else {
                parent[pb] = pa;
                rank[pa]++;
            }

            return true;
        }
    }

    public int maxStability(int n, int[][] edges, int k) {

        int low = 0, high = 200000;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (can(n, edges, k, mid)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    private boolean can(int n, int[][] edges, int k, int target) {

        DSU dsu = new DSU(n);
        int usedEdges = 0;
        int upgrades = 0;

        List<int[]> normal = new ArrayList<>();
        List<int[]> upgrade = new ArrayList<>();

        // mandatory edges
        for (int[] e : edges) {
            int u = e[0], v = e[1], s = e[2], must = e[3];

            if (must == 1) {
                if (s < target) return false;
                if (!dsu.union(u, v)) return false;
                usedEdges++;
            } else {
                if (s >= target) normal.add(e);
                else if (2L * s >= target) upgrade.add(e);
            }
        }

        // add edges without upgrade
        for (int[] e : normal) {
            if (dsu.union(e[0], e[1])) {
                usedEdges++;
                if (usedEdges == n - 1) return true;
            }
        }

        // add edges with upgrade
        for (int[] e : upgrade) {
            if (upgrades == k) break;

            if (dsu.union(e[0], e[1])) {
                upgrades++;
                usedEdges++;
                if (usedEdges == n - 1) return true;
            }
        }

        return usedEdges == n - 1;
    }
}