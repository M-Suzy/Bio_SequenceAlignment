package models;


public interface PAM250 {
    char[] letters= {'A',  'R',  'N',  'D',  'C',  'Q',
            'E', 'G', 'H',  'I', 'L',  'K', 'M',  'F',  'P',
            'S',  'T' , 'W', 'Y',  'V',  'B',  'J',  'Z', 'X' };

    int[][] PAM250Matrix = {
            { 2, -2,  0,  0, -2,  0,  0,  1, -1, -1, -2, -1, -1, -3, 1,  1, 1, -6, -3,  0,  0, -1,  0, -1 },
            {-2,  6,  0, -1, -4,  1, -1, -3,  2, -2, -3,  3,  0, -4,  0,  0, -1,  2, -4, -2, -1, -3, 0, -1 },
            { 0,  0,  2,  2, -4,  1 , 1,  0,  2, -2, -3,  1, -2, -3,  0,  1,  0, -4, -2, -2,  2, -3, 1, -1},
            { 0, -1,  2,  4, -5,  2,  3,  1,  1, -2, -4,  0, -3, -6, -1,  0,  0, -7, -4, -2,  3, -3, 3, -1 },
            {-2, -4, -4, -5, 12, -5, -5, -3, -3, -2, -6, -5, -5, -4, -3,  0, -2, -8, 0, -2, -4, -5, -5, -1},
            { 0, 1,  1,  2, -5,  4,  2, -1,  3, -2, -2, 1, -1, -5,  0, -1, -1, -5, -4, -2, 1, -2, 3, -1,},
            { 0, -1,  1,  3, -5,  2,  4,  0,  1, -2, -3,  0, -2, -5, -1, 0, 0, -7, -4, -2,  3, -3,  3, -1},
            { 1, -3,  0,  1, -3, -1,  0,  5, -2, -3, -4, -2, -3, -5,  0,  1,  0, -7, -5, -1,  0, -4,  0, -1, },
            {-1, 2,  2,  1, -3,  3,  1, -2,  6, -2, -2,  0, -2, -2,  0, -1, -1, -3,  0, -2, 1, -2,  2, -1},
            {-1, -2, -2, -2, -2, -2, -2, -3, -2, 5,  2, -2, 2,  1, -2, -1,  0, -5, -1,  4, -2,  3, -2, -1},
            {-2, -3, -3, -4, -6, -2, -3, -4, -2,  2,  6, -3,  4, 2, -3, -3, -2, -2, -1,  2, -3,  5, -3, -1,},
            {-1 , 3,  1,  0, -5,  1, 0, -2,  0, -2, -3,  5,  0, -5, -1,  0,  0, -3, -4, -2,  1, -3,  0, -1,},
            {-1 ,0, -2, -3, -5, -1, -2, -3, -2,  2,  4, 0,  6,  0, -2, -2, -1, -4, -2,  2, -2,  3, -2, -1},
            {-3, -4, -3, -6, -4, -5, -5, -5, -2,  1,  2, -5,  0,  9, -5, -3, -3,  0,  7, -1, -4,  2, -5, -1,},
            {1 , 0,  0, -1, -3,  0, -1,  0,  0, -2, -3, -1, -2, -5,  6,  1,  0, -6, -5, -1, -1, -2,  0, -1, },
            {1,  0,  1,  0,  0, -1,  0,  1, -1, -1, -3,  0, -2, -3,  1,  2,  1, -2, -3, -1,  0, -2,  0, -1 },
            { 1, -1,  0,  0,-2, -1,  0,  0, -1,  0, -2,  0, -1, -3,  0,  1,  3, -5, -3,  0,  0, -1, -1, -1,},
            {-6,  2, -4, -7, -8, -5, -7, -7, -3, -5, -2, -3, -4, 0, -6, -2, -5, 17,  0, -6, -5, -3, -6, -1},
            {-3, -4, -2, -4, 0, -4, -4, -5,  0, -1, -1, -4, -2,  7, -5, -3, -3,  0, 10, -2, -3, -1, -4, -1},
            {0, -2, -2, -2, -2, -2, -2, -1, -2,  4,  2, -2,  2, -1, -1, -1,  0, -6, -2,  4, -2,  2, -2, -1 },
            {0, -1,  2,  3, -4,  1,  3,  0,  1, -2, -3,  1, -2, -4, -1,  0,  0, -5, -3, -2,  3, -3,  2, -1 },
            {-1, -3, -3, -3, -5, -2, -3, -4, -2,  3,  5, -3,  3,  2, -2, -2, -1, -3, -1,  2, -3,  5, -2, -1},
            {0 , 0,  1,  3, -5,  3,  3,  0,  2, -2, -3,  0, -2, -5,  0,  0, -1, -6, -4, -2,  2, -2,  3, -1 },
            {-1, -1, -1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1, -1, -1, -1}
    };

}
