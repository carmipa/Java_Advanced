import NavBar from "@/compenents/nav-bar";
import { Plus } from "lucide-react"
import { Button } from "@/components/ui/button"
import CategoriesItem from "@/compenents/category-item";

async function getCategories(){
    const response = await fetch("https://localhost:8000/api/categories")
    return await response.json()
}

export default async function CategoriesPage(){

    const data: Category[] = await getCategories()


    return(
        <>
            <NavBar active="categorias"/>

            <main className="Flex justify-center">
                <div className="bg-slate-900 p-6 m-6 rounded min-w-1/3">
                    <div className="flex justify-between">
                        <h2 className="text-lg font-semibold">Categorias</h2>
                        <Button>
                            <Plus />
                            nova categoria
                        </Button>
                    </div>

                    {data.map(category => <CategoryItem key={category.id} category={category} />)}
                    <CategoriesItem />
                    <CategoriesItem />
                    <CategoriesItem />
                </div>
            </main>
        </>

    )
}