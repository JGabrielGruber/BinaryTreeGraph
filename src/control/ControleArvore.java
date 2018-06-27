/*
 * Copyright (C) 2018 José Gabriel Gruber
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package control;

import model.ModeloArvore;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gruber
 */
public class ControleArvore {

    public static ModeloArvore inserirNodo(ModeloArvore nodo, int valor, int camada, int coluna) {
        camada++;
        if (nodo != null && nodo.getValor() == 0 && nodo.getCamada() == 0 && nodo.getPai() == null) {
            nodo.setValor(valor);
            nodo.setCamada(1);
            nodo.setColuna(1);
        } else if (nodo == null) {
            nodo = new ModeloArvore();
            nodo.setValor(valor);
            nodo.setCamada(camada);
            nodo.setColuna(coluna);
            nodo.setFilhoEsquerdo(null);
            nodo.setFilhoDireito(null);
        } else if (valor < nodo.getValor()) {
            if (coluna > 1) {
                coluna *= 2;
                coluna -= 1;
            }
            nodo.setFilhoEsquerdo(inserirNodo(nodo.getFilhoEsquerdo(), valor, camada, coluna));
            nodo.getFilhoEsquerdo().setPai(nodo);
        } else {
            coluna *= 2;
            nodo.setFilhoDireito(inserirNodo(nodo.getFilhoDireito(), valor, camada, coluna));
            nodo.getFilhoDireito().setPai(nodo);
        }
        return nodo;
    }

    public static ModeloArvore corrigir(ModeloArvore nodo) {
        int temp;
        if (nodo.getFilhoEsquerdo() != null && nodo.getFilhoEsquerdo().getValor() > nodo.getValor()) {
            temp = nodo.getValor();
            nodo.setValor(nodo.getFilhoEsquerdo().getValor());
            nodo.getFilhoEsquerdo().setValor(temp);
            if (nodo.getPai() != null) {
                nodo = nodo.getPai();
            }
            nodo = corrigir(nodo);
        } else if (nodo.getFilhoDireito() != null && nodo.getFilhoDireito().getValor() > nodo.getValor()) {
            temp = nodo.getValor();
            nodo.setValor(nodo.getFilhoDireito().getValor());
            nodo.getFilhoDireito().setValor(temp);
            if (nodo.getPai() != null) {
                nodo = nodo.getPai();
            }
            nodo = corrigir(nodo);
        }
        return nodo;
    }

    public static ModeloArvore chegaRaiz(ModeloArvore nodo) {
        if (nodo != null && nodo.getPai() != null) {
            while (nodo.getPai() != null) {
                nodo = nodo.getPai();
            }
        }
        return nodo;
    }

    public static ModeloArvore extrairExclusao(ModeloArvore nodo, List<ModeloArvore> listaNodo) {
        int valor = 0;
        for (ModeloArvore listaNodo1 : listaNodo) {
            valor = listaNodo1.getValor();
            nodo = chegaRaiz(nodo);
            nodo = inserirNodo(nodo, valor, 0, 1);
        }
        return nodo;
    }

    public static ModeloArvore excluir(ModeloArvore nodo, int valor) {
        ArrayList<ModeloArvore> listaNodoDireito = new ArrayList(), listaNodoEsquerdo = new ArrayList();

        if (nodo != null) {
            if (nodo.getValor() == valor) {
                if (nodo.getPai() != null && nodo.getFilhoDireito() != null) {
                    if (nodo.getValor() < nodo.getPai().getValor()) {
                        nodo.getPai().setFilhoEsquerdo(null);
                    } else if (nodo.getValor() >= nodo.getPai().getValor()) {
                        nodo.getPai().setFilhoDireito(null);
                    }
                    nodo.getFilhoDireito().setPai(null);
                    listaNodoDireito = obter(nodo.getFilhoDireito(), listaNodoDireito);

                    if (nodo.getFilhoEsquerdo() != null) {
                        nodo.getFilhoEsquerdo().setPai(null);
                        listaNodoEsquerdo = obter(nodo.getFilhoEsquerdo(), listaNodoEsquerdo);
                    }

                    nodo = chegaRaiz(nodo);
                    nodo = extrairExclusao(nodo, listaNodoDireito);
                    nodo = extrairExclusao(nodo, listaNodoEsquerdo);
                    return nodo;

                } else if (nodo.getPai() != null && nodo.getFilhoEsquerdo() != null) {

                    if (nodo.getValor() < nodo.getPai().getValor()) {
                        nodo.getPai().setFilhoEsquerdo(null);

                    } else if (nodo.getValor() >= nodo.getPai().getValor()) {
                        nodo.getPai().setFilhoDireito(null);
                    }
                    nodo.getFilhoEsquerdo().setPai(null);
                    listaNodoEsquerdo = obter(nodo.getFilhoEsquerdo(), listaNodoEsquerdo);

                    if (nodo.getFilhoDireito() != null) {
                        nodo.getFilhoDireito().setPai(null);
                        listaNodoDireito = obter(nodo.getFilhoDireito(), listaNodoDireito);
                    }

                    nodo = extrairExclusao(nodo, listaNodoEsquerdo);
                    nodo = extrairExclusao(nodo, listaNodoDireito);
                    return nodo;

                } else if (nodo.getPai() != null) {

                    if (nodo.getValor() < nodo.getPai().getValor()) {
                        nodo.getPai().setFilhoEsquerdo(null);

                    } else if (nodo.getValor() >= nodo.getPai().getValor()) {

                        nodo.getPai().setFilhoDireito(null);
                    }

                    return nodo;

                } else if (nodo.getPai() == null) {

                    if (nodo.getFilhoDireito() != null) {

                        listaNodoEsquerdo = new ArrayList();

                        if (nodo.getFilhoEsquerdo() != null) {

                            nodo.getFilhoEsquerdo().setPai(null);
                            listaNodoEsquerdo = obter(nodo.getFilhoEsquerdo(), listaNodoEsquerdo);
                        }

                        nodo = nodo.getFilhoDireito();
                        listaNodoDireito = obter(nodo.getFilhoDireito(), listaNodoDireito);
                        nodo.setPai(null);
                        nodo.setCamada(1);
                        nodo.setColuna(1);
                        nodo.setFilhoDireito(null);
                        nodo.setFilhoEsquerdo(null);
                        nodo = extrairExclusao(nodo, listaNodoDireito);
                        nodo = extrairExclusao(nodo, listaNodoEsquerdo);
                        return nodo;

                    } else if (nodo.getFilhoEsquerdo() != null) {

                        listaNodoDireito = new ArrayList();
                        if (nodo.getFilhoDireito() != null) {

                            nodo.getFilhoDireito().setPai(null);
                            listaNodoDireito = obter(nodo.getFilhoDireito(), listaNodoDireito);
                        }
                        nodo = nodo.getFilhoEsquerdo();
                        listaNodoEsquerdo = obter(nodo.getFilhoEsquerdo(), listaNodoEsquerdo);
                        nodo.setPai(null);
                        nodo.setCamada(1);
                        nodo.setColuna(1);
                        nodo.setFilhoDireito(null);
                        nodo.setFilhoEsquerdo(null);
                        nodo = extrairExclusao(nodo, listaNodoEsquerdo);
                        nodo = extrairExclusao(nodo, listaNodoDireito);
                        return nodo;
                    }
                }
            } else if (nodo.getValor() > valor) {
                nodo = excluir(nodo.getFilhoEsquerdo(), valor);
            } else if (nodo.getValor() <= valor) {
                nodo = excluir(nodo.getFilhoDireito(), valor);
            }
        }
        return nodo;
    }

    public static ArrayList<Integer> obterValoresLista(ArrayList<ModeloArvore> lista, ArrayList<Integer> listaValores) {
        listaValores = new ArrayList<>();
        for (ModeloArvore nodo : lista) {
            listaValores.add(nodo.getValor());
        }
        return listaValores;
    }

    public static int tamanho(List<ModeloArvore> lista) {
        int tamanho = 0;
        for (ModeloArvore lista1 : lista) {
            if (lista1.getCamada() > tamanho) {
                tamanho = lista1.getCamada();
            }
        }
        return tamanho;
    }

    public static String exibir(List<ModeloArvore> lista) {
        String valores = " ";
        for (ModeloArvore lista1 : lista) {
            valores += " | " + lista1.getValor();
        }
        return valores;
    }

    public static ArrayList<ModeloArvore> obter(ModeloArvore nodo, ArrayList<ModeloArvore> valores) {
        valores = new ArrayList<>();
        boolean executarEsquerdo = true, executarDireito = true;
        ModeloArvore temp = new ModeloArvore(), tempEs = new ModeloArvore(),
                tempDi = new ModeloArvore(), tempEsquerdo = new ModeloArvore();
        while (true) {
            if (nodo != null) {
                if (nodo.getPai() == null && nodo.getFilhoDireito() == temp
                        || nodo.getPai() == null && nodo.getFilhoEsquerdo() == tempEsquerdo) {
                    return valores;
                }
                if (nodo.getPai() == null && nodo.getFilhoEsquerdo() != tempEsquerdo) {
                    tempEsquerdo = temp;
                }
                if (nodo.getFilhoEsquerdo() == temp) {
                    tempEs = temp;
                    executarEsquerdo = false;
                    executarDireito = true;
                }
                if (nodo.getFilhoEsquerdo() == tempEs) {
                    executarEsquerdo = false;
                }
                if (nodo.getFilhoDireito() == temp) {
                    tempDi = temp;
                    executarDireito = false;
                }
                if (nodo.getFilhoEsquerdo() != null && executarEsquerdo == true
                        && nodo.getFilhoEsquerdo() != tempEs) {
                    nodo = nodo.getFilhoEsquerdo();
                } else if (nodo.getFilhoDireito() != null && executarDireito == true
                        && nodo.getFilhoEsquerdo() != tempDi) {
                    if (nodo.getFilhoDireito() != null) {
                        valores.add(nodo);
                        executarEsquerdo = true;
                    }
                    nodo = nodo.getFilhoDireito();
                } else {
                    if (nodo.getFilhoDireito() == temp) {
                        executarDireito = true;
                    }
                    temp = nodo;
                    if (nodo.getFilhoDireito() == null || nodo.getPai().getFilhoDireito() != temp) {
                        if (nodo.getFilhoDireito() != null) {
                            if (nodo.getValor() != nodo.getFilhoDireito().getValor()) {
                                valores.add(nodo);
                            }
                        } else {
                            valores.add(nodo);
                        }
                    }
                    nodo = nodo.getPai();
                }
            } else {
                return valores;
            }
        }
    }

    public static String mostrar(ModeloArvore nodo, String valores) {
        boolean executarEsquerdo = true, executarDireito = true;
        ModeloArvore temp = new ModeloArvore(), tempEs = new ModeloArvore(),
                tempDi = new ModeloArvore(), tempEsquerdo = new ModeloArvore();
        while (true) {
            if (nodo != null) {
                if (nodo.getPai() == null && nodo.getFilhoDireito() == temp
                        || nodo.getPai() == null && nodo.getFilhoEsquerdo() == tempEsquerdo) {
                    return valores;
                }
                if (nodo.getPai() == null && nodo.getFilhoEsquerdo() != tempEsquerdo) {
                    tempEsquerdo = temp;
                }
                if (nodo.getFilhoEsquerdo() == temp) {
                    tempEs = temp;
                    executarEsquerdo = false;
                    executarDireito = true;
                }
                if (nodo.getFilhoEsquerdo() == tempEs) {
                    executarEsquerdo = false;
                }
                if (nodo.getFilhoDireito() == temp) {
                    tempDi = temp;
                    executarDireito = false;
                }
                if (nodo.getFilhoEsquerdo() != null && executarEsquerdo == true
                        && nodo.getFilhoEsquerdo() != tempEs) {
                    nodo = nodo.getFilhoEsquerdo();
                } else if (nodo.getFilhoDireito() != null && executarDireito == true
                        && nodo.getFilhoEsquerdo() != tempDi) {
                    if (nodo.getFilhoDireito() != null) {
                        valores += " | " + nodo.getValor();
                        executarEsquerdo = true;
                    }
                    nodo = nodo.getFilhoDireito();
                } else {
                    if (nodo.getFilhoDireito() == temp) {
                        executarDireito = true;
                    }
                    temp = nodo;
                    if (nodo.getFilhoDireito() == null || nodo.getPai().getFilhoDireito() != temp) {
                        if (nodo.getFilhoDireito() != null) {
                            if (nodo.getValor() != nodo.getFilhoDireito().getValor()) {
                                valores += " | " + nodo.getValor();
                            }
                        } else {
                            valores += " | " + nodo.getValor();
                        }
                    }
                    nodo = nodo.getPai();
                }
            } else {
                return valores;
            }
        }
    }

}
