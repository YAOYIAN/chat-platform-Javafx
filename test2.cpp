#include <cstdio>
#include <algorithm>
using namespace std;
int n, m, ans;
struct node
{
    int f, t, d;
} a[200005]; //存边，Kruskal不用建图
bool cmp(node x, node y) { return x.d < y.d; }
struct bin
{
    int w[5005];
    int find(int x)
    {
        if (x == w[x])
            return x;
        w[x] = find(w[x]);
        return w[x];
    }
    void add(int x, int y)
    {
        w[find(x)] = find(y);
        return;
    }
    bool ask(int x, int y)
    {
        if (find(x) == find(y))
            return true;
        else
            return false;
    }
} b; //并查集
int main()
{
    scanf("%d%d", &n, &m);
    for (int i = 1; i <= m; i++)
        scanf("%d%d%d", &a[i].f, &a[i].t, &a[i].d);
    for (int i = 1; i <= n; i++)
        b.w[i] = i;              //并查集初始化
    sort(a + 1, a + m + 1, cmp); //按边权排序
    for (int i = 1; i <= m; i++) //枚举每条边
    {
        if (b.ask(a[i].f, a[i].t))
            continue;          //连通则跳过
        ans += a[i].d;         //否则记录
        b.add(a[i].f, a[i].t); //改为连通
    }
    for (int i = 2; i <= n; i++)
        if (!b.ask(1, n))
        {
            printf("orz");
            return 0;
        } //判断是否连通
    printf("%d", ans);
    return 0;
}